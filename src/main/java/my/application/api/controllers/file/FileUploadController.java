package my.application.api.controllers.file;

import my.application.api.services.file.FileUploadConstants;
import my.application.api.services.file.FileUploadService;
import my.application.api.services.file.PhotoFileUploadServiceImpl;
import my.application.api.services.file.VideoFileUploadServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.procedure.NoSuchParameterException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/file")
public class FileUploadController {

    private final List<FileUploadService> photoFileUploadService;
//    private final FileUploadService videoFileUploadServiceImpl;

    private Optional<FileUploadService> getBeans(String type) {
        FileUploadConstants.FILE_TYPES fileTypes1 = Arrays.stream(FileUploadConstants.FILE_TYPES.values())
                .filter(fileTypes -> fileTypes.getType().equals(type))
                .findFirst()
                .orElseThrow(() -> new NoSuchParameterException("illegal argument : " + type));

        Class<? extends FileUploadService> clazz = getServiceClass(fileTypes1);

        for (FileUploadService service : photoFileUploadService) {
            if (service.getClass().equals(clazz)) {
                return Optional.of(service);
            }
        }
        return Optional.empty();
    }

    private static Class<? extends FileUploadService> getServiceClass(FileUploadConstants.FILE_TYPES fileTypes1) {
        return switch (fileTypes1) {
            case PHOTO -> PhotoFileUploadServiceImpl.class;
            case VIDEO -> VideoFileUploadServiceImpl.class;
        };
    }

    @GetMapping("/{type}/all")
    public List<File> getAllPhoto(@PathVariable String type) throws IOException {
        return getBeans(type).orElseThrow().getAllFiles();
    }

    @PostMapping("/{type}")
    public void uploadPhoto(List<MultipartFile> multipartFiles, @PathVariable String type) throws IOException, InterruptedException {
        getBeans(type).orElseThrow(() -> new NoSuchElementException("not found bean")).uploadFiles(multipartFiles);
    }
}
