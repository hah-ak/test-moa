package my.application.api.controllers.file;

import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.trace.Tracer;
import lombok.extern.slf4j.Slf4j;
import my.application.api.services.file.*;
import org.hibernate.procedure.NoSuchParameterException;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/file")
public class FileUploadController {

    private final List<FileService> photoFileService;
    private final Tracer tracer;

    @Autowired
    public FileUploadController(final List<FileService> photoFileService, OpenTelemetry openTelemetry) {
        this.photoFileService = photoFileService;
        this.tracer = openTelemetry.getTracer(this.getClass().getName());
    }

//    private final FileUploadService videoFileUploadServiceImpl;

    private Optional<FileService> getBeans(String type) {
        FileUtils.FILE_TYPES fileTypes1 = Arrays.stream(FileUtils.FILE_TYPES.values())
                .filter(fileTypes -> fileTypes.getType().equals(type))
                .findFirst()
                .orElseThrow(() -> new NoSuchParameterException("illegal argument : " + type));

        Class<? extends FileService> clazz = getServiceClass(fileTypes1);

        for (FileService service : photoFileService) {
            if (service.getClass().equals(clazz)) {
                return Optional.of(service);
            }
        }
        return Optional.empty();
    }

    private static Class<? extends FileService> getServiceClass(FileUtils.FILE_TYPES fileTypes1) {
        return switch (fileTypes1) {
            case PHOTO -> PhotoFileServiceImpl.class;
            case VIDEO -> VideoFileServiceImpl.class;
            case JSON -> JsonFileServiceImpl.class;
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
