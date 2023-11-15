package my.application.api.services.file;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

@Slf4j
@Service
@RequiredArgsConstructor
public class JsonFileServiceImpl implements FileService {


    @Override
    public void uploadFile(MultipartFile multipartFile) throws IOException {
        final Path path = FileUtils.USER_HOME.resolve(FileUtils.JSON_DIRECTORY).resolve(multipartFile.getName());
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
        try (Stream<Path> pathStream = Files.list(FileUtils.USER_HOME.resolve(FileUtils.JSON_DIRECTORY))) {
            return pathStream.map(Path::toFile).toList();
        }
    }

    @Override
    public File getFile(String fileName) {
        return FileUtils.USER_HOME.resolve(FileUtils.JSON_DIRECTORY).resolve(fileName).toFile();
    }
}
