package my.application.streaming.services;

import com.google.common.base.Strings;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

@Slf4j
@Service
public class NaverVideoDownload implements VideoDownload{
    // lsu_sa 값이 바뀌므로 그때 그때 url을 받아와서 다운로드해야하는듯

    private final String SERVICE_DIR = "/naver";
    private final RestTemplate restTemplate = new RestTemplate();
    @PostConstruct
    public void setDefaultDir() throws IOException {
        Path fullDownloadPath = Path.of(HOME + VIDEO_PATH + DOWNLOAD_PATH);
        if (Files.notExists(fullDownloadPath)) {
            Files.createDirectory(fullDownloadPath);
        }
        Path naverDownloadDir = Path.of(fullDownloadPath.toString(), SERVICE_DIR);
        if (Files.notExists(naverDownloadDir)) {
            Files.createDirectory(naverDownloadDir);
        }
    }
    @Override
    public void createFile(String url) throws IOException {
        URL url1 = new URL(url);
        int lastSlashIndex = url1.getPath().lastIndexOf("/");
        String nonTsPath = url1.getPath().substring(0, lastSlashIndex + 1);
        String tsFile = url1.getPath().substring(lastSlashIndex + 1);
        int i = tsFile.lastIndexOf("-");

        String folderName = tsFile.substring(0,i);
        String ts = tsFile.substring(i + 1);
        int pad = ts.split("\\.")[0].length();

        Path dir = Path.of(HOME + VIDEO_PATH + DOWNLOAD_PATH + SERVICE_DIR + "/" + folderName);
        if (Files.notExists(dir)) {
            Files.createDirectory(dir);
        }


        int cnt = 0;
        while (cnt < 2) {
            try {
                String tsIdx = Strings.padStart("" + cnt,pad,'0');
                String requestUrl = url1.getProtocol() + "://" +url1.getHost() + nonTsPath + folderName + "-" + tsIdx + ".ts?" + url1.getQuery();
                String content = sendRequest(requestUrl);
                Path newFile = Files.createFile(Path.of(dir + "/" +tsIdx));

                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(newFile.toFile()));
                bufferedWriter.write(new String(content.getBytes(StandardCharsets.UTF_8)));
                bufferedWriter.flush();
                cnt++;
            } catch (Exception e) {
                e.printStackTrace();
//                log.error("{}", e.getMessage());
                break;
            }
        }

    }

    public String sendRequest(String url) throws HttpStatusCodeException {
        ResponseEntity<String> exchange = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
        if (exchange.getStatusCode().is2xxSuccessful()) {
            return exchange.getBody();
        }
        throw new HttpServerErrorException(exchange.getStatusCode());
    }
}
