package my.application.streaming.services.download;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@Service
public class NaverVideoDownload extends VideoDownloadAbstract {
    public NaverVideoDownload() {
        super("/naver");
    }
    @PostConstruct
    public void setDefault() throws IOException {
        super.setDefaultDir();
    }

}
