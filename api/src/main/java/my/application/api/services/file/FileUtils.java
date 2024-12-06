package my.application.api.services.file;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;

@Slf4j
@Component
public class FileUtils {
    public static final Path USER_HOME = Paths.get(System.getProperty("user.home")).toAbsolutePath();
    public static final Path USER_DIR = Paths.get(System.getProperty("user.dir")).toAbsolutePath();
    public static final DateTimeFormatter FILE_DATE_TIME_FORMAT= DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    public static final String FILE_DIRECTORY = "service_files";
    public static final String PHOTO_DIRECTORY = FILE_DIRECTORY + "\\photos";
    public static final String JSON_DIRECTORY = FILE_DIRECTORY + "\\jsons";
    public static final String VIDEO_DIRECTORY = FILE_DIRECTORY + "\\videos";

    static {
        for (String path : new String[]{FILE_DIRECTORY,JSON_DIRECTORY, VIDEO_DIRECTORY, PHOTO_DIRECTORY}) {
            try {
                Path resolve = USER_HOME.resolve(path);
                Files.createDirectory(resolve);
            } catch (FileAlreadyExistsException fileAlreadyExistsException) {
                if (!Files.isDirectory(Paths.get(fileAlreadyExistsException.getFile()))) {
                    throw new RuntimeException(fileAlreadyExistsException);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    @Getter
    @AllArgsConstructor
    public enum FILE_TYPES{
        VIDEO("video"),PHOTO("photo"),JSON("json");
        private final String type;
    }

}
