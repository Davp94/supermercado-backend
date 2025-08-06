package com.blumbit.supermercado.dto.request;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileRequest {
    @NotNull(message = "Archivo requerido")
    private MultipartFile file;
}
