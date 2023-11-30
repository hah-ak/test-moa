package my.application.streaming.services;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class VideoConvertServiceTest {

    VideoConvertService videoConvertService = new VideoConvertService();

    @BeforeAll
    static void befor() {
        new VideoConvertService().createDefaultDir();
    }

    @Test
    void convertFile() throws IOException {
        videoConvertService.convertFile("C:\\Users\\PC\\Desktop\\videos\\개웃김.mp4");
        // 일반적인 동영상 스트리밍은 hls파일들을 일정간격으로 호출해서 받아감.
    }
    // https://b01-kr-naver-vod.pstatic.net/cafe/a/read/v2/VOD_ALPHA/cafe_2023_01_28_812/hls/7b4a68d9-9eb0-11ed-8bdf-80615f0bcbc8-000023.ts?_lsu_sa_=6985faf9a1946c06a1da05aa60d507b23eba3338f90b8f3a3bb734cfe74033b55c277abf6025be0230423a7217309b9901d21a001f852823acb7b69f5fd2a06f1c20323bb312ebb230477f30d0343ba34fbe8cfcdc0d0cd0727e36e7eeb5634207811df052fdbfcf21b4620cae3b1c85658166f4af2efa0d5bcdc9603a0311ad
    @Test
    void createFile() {

    }

}