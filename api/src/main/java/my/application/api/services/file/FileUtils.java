package my.application.api.services.file;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;

public class FileUtils {
    public static final Path USER_HOME = Paths.get(System.getProperty("user.home")).toAbsolutePath();
    public static final Path USER_DIR = Paths.get(System.getProperty("user.dir")).toAbsolutePath();
    public static final DateTimeFormatter FILE_DATE_TIME_FORMAT= DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    public static final String PHOTO_DIRECTORY = "Desktop\\photos";
    public static final String JSON_DIRECTORY = "Desktop\\jsons";
    public static final String VIDEO_DIRECTORY = "Desktop\\videos";
    @PostConstruct
    private void setDefaultPath() throws IOException {
        for (String path : new String[]{FileUtils.JSON_DIRECTORY, FileUtils.VIDEO_DIRECTORY, FileUtils.PHOTO_DIRECTORY}) {
            Path resolve = FileUtils.USER_HOME.resolve(path);
            if (!Files.isDirectory(resolve)) {
                Files.createDirectory(resolve);
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
