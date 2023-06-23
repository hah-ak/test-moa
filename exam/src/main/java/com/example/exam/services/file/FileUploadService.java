package com.example.exam.services.file;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public interface FileUploadService {
    void uploadFile(MultipartFile multipartFile) throws IOException;
    void uploadFiles(List<MultipartFile> multipartFiles) throws IOException, InterruptedException;
    List<File> getAllFiles() throws IOException;
    File getFile(String fileName);
}
