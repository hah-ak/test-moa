package my.application.streaming.services.convert;

import jakarta.annotation.PostConstruct;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import my.application.streaming.services.VideoService;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@Service
public class VideoConvertService implements VideoService {
    // ffmpeg cli 를 사용하기 위한 커넥터 클래스.
    private final String STREAM_PATH = VIDEO_PATH + "/stream";
    private final String EXTENSION = "m3u8";
    private final String SHELL;
    private final String COMMAND_OPTION;

    @PostConstruct
    public void createDefaultDir() {
        try {
            Path video = Path.of(HOME + VIDEO_PATH);
            if (Files.notExists(video)) {
                Files.createDirectory(video);
            }
            Path stream = Path.of(HOME + STREAM_PATH);
            if (Files.notExists(stream)) {
                Files.createDirectory(stream);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public VideoConvertService() {
        SHELL = System.getProperty("os.name").toLowerCase().contains("window") ? WINDOW_SEHLL : DEFAULT_SHELL;
        COMMAND_OPTION =  System.getProperty("os.name").toLowerCase().contains("window") ? WINDOW_COMMAND_OPTION : DEFAULT_COMMAND_OPTION;
    }
    public void convertFile(String filePath) throws IOException {
        //codex 을 libx264를 써서 h264인코딩을 한다.
//        ffmpeg -i %s -codec: copy -start_number 0 -hls_time 10 -hls_list_size 0 -f hls %s
        String dirName = createDirName();
        Path directory = createDirectory(dirName);
        String m3u8 = String.format("ffmpeg -i %s -codec:v h264 -start_number 0 -hls_time 10 -hls_segment_filename '%s_%%06d.ts' -f hls %s", filePath, directory + "/" + dirName, directory + "/" + dirName + "." + EXTENSION);
        Process exec = Runtime.getRuntime().exec(SHELL + " " + COMMAND_OPTION + " " + m3u8);

        try (BufferedReader bufferedReader = new BufferedReader(exec.errorReader())) {
            StringBuilder allLine = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                allLine.append(line);
            }
        }
    }
    private String createDirName() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
    }

    public Path createDirectory(String dirName) {
        String directoryPath = STREAM_PATH + "/" + dirName;
        Path finalDirectoryPath = Paths.get(HOME + "/" + directoryPath);
        try {
            if (Files.notExists(finalDirectoryPath)) {
                Files.createDirectory(finalDirectoryPath);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return finalDirectoryPath;
    }

}
