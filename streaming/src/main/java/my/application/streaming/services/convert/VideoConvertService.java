package my.application.streaming.services.convert;

import lombok.extern.slf4j.Slf4j;
import my.application.streaming.services.VideoService;
import my.application.streaming.services.download.VideoDownload;
import my.application.streaming.services.download.VideoDownloadAbstract;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.*;

@Slf4j
@Service
public class VideoConvertService implements VideoService {
    // ffmpeg cli 를 사용하기 위한 커넥터 클래스.
    private static final String STREAM_PATH = VIDEO_PATH + "/stream";
    private static final String EXTENSION = "m3u8";
    private static final String SHELL;
    private static final String COMMAND_OPTION;
    private static final ThreadPoolExecutor threadPoolExecutor;

    static {
        SHELL = System.getProperty("os.name").toLowerCase().contains("window") ? WINDOW_SEHLL : DEFAULT_SHELL;
        COMMAND_OPTION =  System.getProperty("os.name").toLowerCase().contains("window") ? WINDOW_COMMAND_OPTION : DEFAULT_COMMAND_OPTION;

        // core pool size 공식 cpu 코어 * cpu 사용률 * ( 1 + i/o 입력시간 대기효율) $ i/o 입력시간 대기효율 == 대기시간 / 로직처리 시간
        int corePoolSize = (int) (6 * 0.5 * ( 1 + 1 ));
        threadPoolExecutor = new ThreadPoolExecutor(corePoolSize, 12, 10, TimeUnit.SECONDS, new PriorityBlockingQueue<>(30));

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
    public void convertFile(String filePath) throws IOException, ExecutionException, InterruptedException {
        //codex 을 libx264를 써서 h264인코딩을 한다.
//        ffmpeg -i %s -codec: copy -start_number 0 -hls_time 10 -hls_list_size 0 -f hls %s
        String dirName = createDirName();
        Path directory = createDirectory(dirName);
        String m3u8 = String.format("ffmpeg -i %s -codec:v h264 -start_number 0 -hls_time 10 -hls_segment_filename '%s_%%06d.ts' -f hls %s",
                filePath, directory + "/" + dirName, directory + "/" + dirName + "." + EXTENSION);

        Process exec = threadPoolExecutor.submit(() -> Runtime.getRuntime().exec(SHELL + " " + COMMAND_OPTION + " " + m3u8)).get();

        try (BufferedReader bufferedReader = new BufferedReader(exec.errorReader())) {
            StringBuilder allLine = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                allLine.append(line);
            }
        }

        int exit = exec.exitValue();

        System.out.println(exit);
    }
    private String createDirName() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
    }

    public Path createDirectory(String dirName) throws IOException {
        String directoryPath = STREAM_PATH + "/" + dirName;
        Path finalDirectoryPath = Paths.get(HOME + "/" + directoryPath);

        if (Files.notExists(finalDirectoryPath)) {
            Files.createDirectory(finalDirectoryPath);
        }

        return finalDirectoryPath;
    }

    public void customConvert() {
        new VideoDownload() {
            @Override
            public void createFile(String url) throws IOException {

            }
        };

        new VideoDownloadAbstract("") {
            @Override
            public void setDefaultDir() throws IOException {
                super.setDefaultDir();
            }

            @Override
            public void createFile(String m3u8Url) throws IOException {
                super.createFile(m3u8Url);
            }

            @Override
            public List<String> getM3U8Info(String m3u8Url) throws InvalidObjectException {
                return super.getM3U8Info(m3u8Url);
            }

            @Override
            public <T> T sendRequest(String url, Class<T> returnClass) throws HttpStatusCodeException {
                return super.sendRequest(url, returnClass);
            }
        };
    }

}
