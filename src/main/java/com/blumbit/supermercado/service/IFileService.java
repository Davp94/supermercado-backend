package com.blumbit.supermercado.service;

import java.io.File;

import com.blumbit.supermercado.dto.request.FileRequest;
import com.blumbit.supermercado.dto.response.FileDownloadResponse;
import com.blumbit.supermercado.dto.response.FileResponse;

public interface IFileService {
    FileResponse createFile(FileRequest fileRequest);
    File retrieveFile(FileResponse fileResponse);
    FileDownloadResponse fileDownload(String filePath);
}
