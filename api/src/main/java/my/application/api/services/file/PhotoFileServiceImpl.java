package my.application.api.services.file;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.function.Consumer;
import java.util.stream.Stream;

@Slf4j
@Service("photoFileUploadServiceImpl")
@RequiredArgsConstructor
public class PhotoFileServiceImpl implements FileService {

    @Qualifier("fileUpload")
    private final ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Override
    public void uploadFile(MultipartFile multipartFile) throws IOException {

        final Path FILE_PATH = FileUtils.USER_HOME
                .resolve(FileUtils.PHOTO_DIRECTORY)
                .resolve(ZonedDateTime.now(ZoneId.of("UTC")).format(FileUtils.FILE_DATE_TIME_FORMAT) + multipartFile.getName());

        boolean exists = Files.exists(FILE_PATH);

        if (exists) {
            throw new FileAlreadyExistsException(FILE_PATH.toString());
        }
        multipartFile.transferTo(FILE_PATH);
    }

    @Override
    public void uploadFiles(List<MultipartFile> multipartFiles) throws IOException, InterruptedException {

        List<String> ERROR_LIST = new ArrayList<>();
        CountDownLatch countDownLatch = new CountDownLatch(multipartFiles.size());

        Consumer<MultipartFile> fileConsumer = (multipartFile) -> {
            countDownLatch.countDown();
            try {
                uploadFile(multipartFile);
            } catch (IOException e) {
                ERROR_LIST.add(multipartFile.getName());
            }
        };

        for (MultipartFile multipartFile : multipartFiles) {
            threadPoolTaskExecutor.execute(() -> fileConsumer.accept(multipartFile));
        }

        countDownLatch.await();

        if (ERROR_LIST.size() > 0) {
            throw new IOException("upload 중 에러 : " + String.join(",",ERROR_LIST));
        }
    }

    @Override
    public List<File> getAllFiles() throws IOException {
        try (Stream<Path> fileList = Files.list(FileUtils.USER_HOME.resolve(FileUtils.PHOTO_DIRECTORY).toAbsolutePath())) {
            return fileList.map(Path::toFile).toList();
        }
    }

    @Override
    public File getFile(String fileName) {
        return null;
    }
}
