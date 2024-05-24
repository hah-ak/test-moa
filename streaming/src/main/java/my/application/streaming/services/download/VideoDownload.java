package my.application.streaming.services.download;

import my.application.streaming.services.VideoService;

import java.io.IOException;

public interface VideoDownload extends VideoService {

    String DOWNLOAD_PATH = "/download";

    void createFile(String url) throws IOException;
}
