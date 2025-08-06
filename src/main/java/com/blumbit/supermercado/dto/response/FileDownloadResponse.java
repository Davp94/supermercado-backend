package com.blumbit.supermercado.dto.response;

import org.springframework.core.io.Resource;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FileDownloadResponse {

    private Resource resource;
    private String contentType;
    private String fileName;

}
