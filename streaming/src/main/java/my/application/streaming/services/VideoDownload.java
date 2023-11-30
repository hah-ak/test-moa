package my.application.streaming.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;

public interface VideoDownload extends VideoService{

    String DOWNLOAD_PATH = "/download";

    void createFile(String url) throws IOException;
}
