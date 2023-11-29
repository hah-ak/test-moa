package my.application.streaming.services;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Service
public class FileConvert {
    // ffmpeg cli 를 사용하기 위한 커넥터 클래스.
    private final String window = "C:\\Windows\\System32\\WindowsPowerShell\\v1.0\\powershell.exe ";
    private final String videoPath = "/video";
    private final String streamPath = videoPath + "/stream";
    private final String extension = "m3u8";
    private final String defaultShell = "/bin/sh ";
    private final Path home = Paths.get(System.getProperty("user.home")).toAbsolutePath();
    private final String shell;

    @PostConstruct
    public void createDefaultDir() {
        try {
            Path video = Path.of(home + videoPath);
            if (Files.notExists(video)) {
                Files.createDirectory(video);
            }
            Path stream = Path.of(home + streamPath);
            if (Files.notExists(stream)) {
                Files.createDirectory(stream);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public FileConvert() {
        shell = System.getProperty("os.name").toLowerCase().contains("window") ? window : defaultShell;
    }
    public void convertFile(String file) throws IOException {
        String m3u8 = String.format("ffmpeg -i %s -codec: copy -start_number 0 -hls_time 10 -hls_list_size 0 -f hls %s", file, createDirectory());
        Process exec = Runtime.getRuntime().exec(shell + m3u8);
        try (BufferedReader bufferedReader = new BufferedReader(exec.errorReader())) {
            StringBuilder allLine = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                allLine.append(line);
            }
        }

    }

    public String createDirectory() {
        String yyyyMMddHHmmss = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));

        String directoryPath = streamPath + "/" + yyyyMMddHHmmss.substring(0,9);
        Path finalDirectoryPath = Paths.get(home + "/" + directoryPath);
        try {
            if (Files.notExists(finalDirectoryPath)) {
                Files.createDirectory(finalDirectoryPath);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return finalDirectoryPath + "/" + yyyyMMddHHmmss + "." + extension;
    }
}
