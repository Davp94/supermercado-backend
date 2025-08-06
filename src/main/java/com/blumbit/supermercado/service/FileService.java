package com.blumbit.supermercado.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.blumbit.supermercado.dto.request.FileRequest;
import com.blumbit.supermercado.dto.response.FileDownloadResponse;
import com.blumbit.supermercado.dto.response.FileResponse;
import com.blumbit.supermercado.exception.FileStorageException;

import jakarta.annotation.PostConstruct;

@Service
public class FileService implements IFileService{

    @Value("${file.path}")
    private String uploadDir;

    private Path fileStorageLocation;

    @PostConstruct
    public void init() {
        this.fileStorageLocation = Paths.get(uploadDir).toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception e) {
            throw new FileStorageException("No se puede crear el directorio para alamcenar los archivos");
        }
    }

    @Override
    public FileResponse createFile(FileRequest fileRequest) {

        MultipartFile file = fileRequest.getFile();
        String uniqueFileName = generateUniqueFileName(file.getOriginalFilename());
        try {
            Path targetLocation = fileStorageLocation.resolve(uniqueFileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return FileResponse.builder().filePath(uniqueFileName).build();
        } catch (IOException e) {
            throw new FileStorageException("Error al crear el archivo");
        }
    }

    @Override
    public File retrieveFile(FileResponse fileResponse) {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileResponse.getFilePath()).normalize();
            if (!filePath.startsWith(this.fileStorageLocation)) {
                throw new FileStorageException("No se puede acceder al directorio externo");
            }
            File file = filePath.toFile();
            if (!file.exists()) {
                throw new FileNotFoundException("Archivo no encontrado");
            }
            return file;
        } catch (Exception e) {
            throw new FileStorageException("Error recuperando el archivo");
        }
    }

    @Override
    public FileDownloadResponse fileDownload(String filePath) {
        FileResponse fileResponse = FileResponse.builder().filePath(filePath).build();
        File file = retrieveFile(fileResponse);
        try {
            Path path = Paths.get(file.getAbsolutePath());
            Resource resource = new UrlResource(path.toUri());
            if(!resource.exists() || !resource.isReadable()){
                throw new FileNotFoundException("Archivo no accesible");
            }
            String contentType = determineContentType(path); 
            return FileDownloadResponse.builder()
                    .resource(resource)
                    .contentType(contentType)
                    .fileName(file.getName())
                    .build();
        } catch (Exception e) {
            throw new FileStorageException("Error devolviendo el archivo");
        }
    }

    private String generateUniqueFileName(String originalFileName){
        if(originalFileName == null){
            originalFileName = "file";
        }
        String timestamp=new SimpleDateFormat("ddMMyyyy_HHmmss").format(new Date());
        String uuid = UUID.randomUUID().toString().substring(0,8);

        return timestamp + "_" + uuid + "_" + originalFileName;
    }

    private String determineContentType(Path path){
        try {
            String contentType = Files.probeContentType(path);
            return contentType;
        } catch (Exception e) {
            return "application/octet-stream";
        }
    }


}
