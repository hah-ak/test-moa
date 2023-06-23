package com.example.exam.services.file;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class VideoFileUploadServiceImpl implements FileUploadService{

    private final String VIDEO_DIRECTORY = "Desktop\\videos";
    @Qualifier("fileUpload")
    private final ThreadPoolTaskExecutor threadPoolTaskExecutor;
    @Override
    public void uploadFile(MultipartFile multipartFile) throws IOException {

    }

    @Override
    public void uploadFiles(List<MultipartFile> multipartFiles) throws IOException, InterruptedException {

    }

    @Override
    public List<File> getAllFiles() throws IOException {

        try (Stream<Path> videos = Files.list(FileUploadConstants.USER_HOME.resolve(VIDEO_DIRECTORY));) {
            return videos.map(Path::toFile).toList();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public File getFile(String fileName) {
        return null;
    }
}
