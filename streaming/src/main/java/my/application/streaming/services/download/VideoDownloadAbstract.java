package my.application.streaming.services.download;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public abstract class VideoDownloadAbstract implements VideoDownload {

    protected final String SERVICE_DIR;
    protected final RestTemplate restTemplate = new RestTemplate();

    protected VideoDownloadAbstract(String SERVICE_DIR) {
        this.SERVICE_DIR = SERVICE_DIR;
    }
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
    public void createFile(String m3u8Url) throws IOException {

        List<String> m3U8Info = getM3U8Info(m3u8Url);

        int lastSlashIndex = m3u8Url.lastIndexOf("/");
        int questionIdx = m3u8Url.indexOf("?");

        String m3u8FIleName = m3u8Url.substring(lastSlashIndex + 1, questionIdx);
        String staticRequestUrl = m3u8Url.substring(0,lastSlashIndex + 1);
        String folderName = m3u8FIleName.split(".m3u8")[0];

        Path dir = Path.of(HOME + VIDEO_PATH + DOWNLOAD_PATH + SERVICE_DIR + "/" + folderName);
        if (Files.notExists(dir)) {
            Files.createDirectory(dir);
        }
//        m3U8Info.size()
        for (int i = 0; i < m3U8Info.size(); i++) {
            byte[] content = sendRequest(staticRequestUrl + m3U8Info.get(i),byte[].class);
            Path newFile = Files.createFile(Path.of(dir + "/" + i + ".ts"));

            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(newFile.toFile()));
            bufferedOutputStream.write(content);
            bufferedOutputStream.flush();
        }
    }

    public List<String> getM3U8Info(String m3u8Url) throws InvalidObjectException {
        String s = sendRequest(m3u8Url, String.class);
        String[] split = s.split("\n");

        if (!split[0].equals("#EXTM3U")) {
            throw new InvalidObjectException("not m3u8 response");
        }
        ArrayList<String> strings = new ArrayList<>();
        for (String string : split) {
            if(string.charAt(0) != '#') {
                strings.add(string);
            }
        }
        return strings;
    }

    public <T> T sendRequest(String url,Class<T> returnClass) {
        ResponseEntity<T> exchange = restTemplate.exchange(url, HttpMethod.GET, null, returnClass);
        if (exchange.getStatusCode().is2xxSuccessful()) {
            return exchange.getBody();
        }
        throw new HttpServerErrorException(exchange.getStatusCode());
    }


}
