package com.blumbit.supermercado.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blumbit.supermercado.common.dto.CustomResponse;
import com.blumbit.supermercado.dto.request.FileRequest;
import com.blumbit.supermercado.dto.response.FileDownloadResponse;
import com.blumbit.supermercado.dto.response.FileResponse;
import com.blumbit.supermercado.service.IFileService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/file")
public class FileController {

    private final IFileService fileService;

    public FileController(IFileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping
    public CustomResponse<FileResponse> uploadFile(@ModelAttribute @Valid FileRequest fileRequest) {
        try {
            FileResponse response = fileService.createFile(fileRequest);
            return CustomResponse.success(response);
        } catch (Exception e) {
            throw e;
        }
    }

    @GetMapping
    public ResponseEntity<Resource> downloadFile(@RequestParam String filePath) {
        try {
            FileDownloadResponse downloadResponse = fileService.fileDownload(filePath);
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(downloadResponse.getContentType()))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment: filename="+downloadResponse.getFileName())
                    .body(downloadResponse.getResource());
        } catch (Exception e) {
            throw e;
        }
    }
    
    
}
