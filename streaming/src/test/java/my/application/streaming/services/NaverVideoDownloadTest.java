package my.application.streaming.services;

import my.application.streaming.services.download.NaverVideoDownload;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InvalidObjectException;

class NaverVideoDownloadTest {
    // https://b01-kr-naver-vod.pstatic.net/cafe/a/read/v2/VOD_ALPHA/cafe_2023_01_28_812/hls/7b4a68d9-9eb0-11ed-8bdf-80615f0bcbc8-000004.ts?_lsu_sa_=6ee598f1f1f467f6d3d3658161f521b8ce7237e8120f4f953d979cc0978a33d5c42e9ae363c50404d04236e2293b0bdd5baff6e50cd8d5cfeeaa65d6f07f9ad7c3f7d15840d052ec4e68c7244da9822a7618ed2f6b838f191946bafa74f4f859c230b5a7ea3123c24e0f4862405f1582f3c613d6e54bb000c0ad69679389ed9e
    // https://b01-kr-naver-vod.pstatic.net/cafe/a/read/v2/VOD_ALPHA/cafe_2023_01_28_812/hls/7b4a68d9-9eb0-11ed-8bdf-80615f0bcbc8-000023.ts?_lsu_sa_=6985faf9a1946c06a1da05aa60d507b23eba3338f90b8f3a3bb734cfe74033b55c277abf6025be0230423a7217309b9901d21a001f852823acb7b69f5fd2a06f1c20323bb312ebb230477f30d0343ba34fbe8cfcdc0d0cd0727e36e7eeb5634207811df052fdbfcf21b4620cae3b1c85658166f4af2efa0d5bcdc9603a0311ad



//    https://naver-tvchosun-h.smartmediarep.com/smc/naver/adaptive/eng/CS1_666123/2f747663686f73756e2f4353322f323032335f707265766965772f656e7465722f433230323330303132365f636c69702f433230323330303132365f636c69705f32303233313132375f3130725f382e736d696c/0-0-0/content_5120000_9.ts?solexpire=1701434058&solpathlen=209&soltoken=db175b7de15391d89994830a34dd59ee&soltokenrule=c29sZXhwaXJlfHNvbHBhdGhsZW58c29sdXVpZA==&soluriver=2&soluuid=5838272e-3f87-4f66-95af-e5ca79dbaf7e
    public static NaverVideoDownload naverVideoDownload;

    @BeforeAll
    static void setdefault() throws IOException {
        naverVideoDownload = new NaverVideoDownload();
        naverVideoDownload.setDefaultDir();
    }
    @Test
    void createFile() throws IOException {

        naverVideoDownload.createFile("https://naver-tvchosun-h.smartmediarep.com/smc/naver/adaptive/eng/CS1_666123/2f747663686f73756e2f4353322f323032335f707265766965772f656e7465722f433230323330303132365f636c69702f433230323330303132365f636c69705f32303233313132375f3130725f382e736d696c/0-0-0/content_5120000.m3u8?solexpire=1701436590&solpathlen=209&soltoken=a1737018d04d4da6cc1b7a720e67c421&soltokenrule=c29sZXhwaXJlfHNvbHBhdGhsZW58c29sdXVpZA==&soluriver=2&soluuid=09955c70-e2cd-48fb-9b3e-200e22636668");
    }
//    https://naver-tvchosun-h.smartmediarep.com/smc/naver/adaptive/eng/CS1_666123/2f747663686f73756e2f4353322f323032335f707265766965772f656e7465722f433230323330303132365f636c69702f433230323330303132365f636c69705f32303233313132375f3130725f382e736d696c/0-0-0/content_5120000.m3u8?solexpire=1701436590&solpathlen=209&soltoken=a1737018d04d4da6cc1b7a720e67c421&soltokenrule=c29sZXhwaXJlfHNvbHBhdGhsZW58c29sdXVpZA==&soluriver=2&soluuid=09955c70-e2cd-48fb-9b3e-200e22636668
    @Test
    void getM3U8Info() throws InvalidObjectException {
        naverVideoDownload.getM3U8Info("https://naver-tvchosun-h.smartmediarep.com/smc/naver/adaptive/eng/CS1_666123/2f747663686f73756e2f4353322f323032335f707265766965772f656e7465722f433230323330303132365f636c69702f433230323330303132365f636c69705f32303233313132375f3130725f382e736d696c/0-0-0/content_1024000.m3u8?solexpire=1701434058&solpathlen=209&soltoken=db175b7de15391d89994830a34dd59ee&soltokenrule=c29sZXhwaXJlfHNvbHBhdGhsZW58c29sdXVpZA==&soluriver=2&soluuid=5838272e-3f87-4f66-95af-e5ca79dbaf7e");
    }
}