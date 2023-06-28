package my.application.api.services.file;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface FileService {
    void uploadFile(MultipartFile multipartFile) throws IOException;
    void uploadFiles(List<MultipartFile> multipartFiles) throws IOException, InterruptedException;
    List<File> getAllFiles() throws IOException;
    File getFile(String fileName);
}
