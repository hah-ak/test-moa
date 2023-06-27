package my.application.api.services.file;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;

public class FileUploadConstants {

    public enum FILE_TYPES{
        VIDEO("video"),PHOTO("photo");
        private String type;

        FILE_TYPES(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }
    }

    public static final Path USER_HOME = Paths.get(System.getProperty("user.home")).toAbsolutePath();
    public static final Path USER_DIR = Paths.get(System.getProperty("user.dir")).toAbsolutePath();
    public static final DateTimeFormatter FILE_DATE_TIME_FORMAT= DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
}
