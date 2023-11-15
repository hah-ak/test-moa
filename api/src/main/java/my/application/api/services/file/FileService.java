package my.application.api.services.file;

import com.google.api.client.http.FileContent;
import jakarta.annotation.PostConstruct;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public interface FileService {
    void uploadFile(MultipartFile multipartFile) throws IOException;
    void uploadFiles(List<MultipartFile> multipartFiles) throws IOException, InterruptedException;
    List<File> getAllFiles() throws IOException;
    File getFile(String fileName);
}
