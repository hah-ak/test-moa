package com.example.exam.services.file;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

@Slf4j
@Service
@RequiredArgsConstructor
public class JsonFileServiceImpl implements FileService {

    protected final String JSON_DIRECTORY = "Desktop\\jsons";
    @Override
    public void uploadFile(MultipartFile multipartFile) throws IOException {
        final Path path = FileConstants.USER_HOME.resolve(JSON_DIRECTORY).resolve(multipartFile.getName());
        multipartFile.transferTo(path);
    }

    @Override
    public void uploadFiles(List<MultipartFile> multipartFiles) throws IOException, InterruptedException {
        multipartFiles.forEach(multipartFile -> {
            try {
                this.uploadFile(multipartFile);
            } catch (IOException e) {
                log.error("json upload error : {}", e.getMessage());
            }
        });
    }

    @Override
    public List<File> getAllFiles() throws IOException {
        try (Stream<Path> pathStream = Files.list(FileConstants.USER_HOME.resolve(JSON_DIRECTORY))) {
            return pathStream.map(Path::toFile).toList();
        }
    }

    @Override
    public File getFile(String fileName) {
        return FileConstants.USER_HOME.resolve(JSON_DIRECTORY).resolve(fileName).toFile();
    }
}
