package my.application.streaming.services;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class NaverVideoDownloadTest {
    // https://b01-kr-naver-vod.pstatic.net/cafe/a/read/v2/VOD_ALPHA/cafe_2023_01_28_812/hls/7b4a68d9-9eb0-11ed-8bdf-80615f0bcbc8-000004.ts?_lsu_sa_=6ee598f1f1f467f6d3d3658161f521b8ce7237e8120f4f953d979cc0978a33d5c42e9ae363c50404d04236e2293b0bdd5baff6e50cd8d5cfeeaa65d6f07f9ad7c3f7d15840d052ec4e68c7244da9822a7618ed2f6b838f191946bafa74f4f859c230b5a7ea3123c24e0f4862405f1582f3c613d6e54bb000c0ad69679389ed9e
    // https://b01-kr-naver-vod.pstatic.net/cafe/a/read/v2/VOD_ALPHA/cafe_2023_01_28_812/hls/7b4a68d9-9eb0-11ed-8bdf-80615f0bcbc8-000023.ts?_lsu_sa_=6985faf9a1946c06a1da05aa60d507b23eba3338f90b8f3a3bb734cfe74033b55c277abf6025be0230423a7217309b9901d21a001f852823acb7b69f5fd2a06f1c20323bb312ebb230477f30d0343ba34fbe8cfcdc0d0cd0727e36e7eeb5634207811df052fdbfcf21b4620cae3b1c85658166f4af2efa0d5bcdc9603a0311ad
    public static NaverVideoDownload naverVideoDownload;

    @BeforeAll
    static void setdefault() throws IOException {
        naverVideoDownload = new NaverVideoDownload();
        naverVideoDownload.setDefaultDir();
    }
    @Test
    void createFile() throws IOException {

        naverVideoDownload.createFile("https://b01-kr-naver-vod.pstatic.net/cafe/a/read/v2/VOD_ALPHA/cafe_2023_01_28_812/hls/7b4a68d9-9eb0-11ed-8bdf-80615f0bcbc8-000004.ts?_lsu_sa_=6ee598f1f1f467f6d3d3658161f521b8ce7237e8120f4f953d979cc0978a33d5c42e9ae363c50404d04236e2293b0bdd5baff6e50cd8d5cfeeaa65d6f07f9ad7c3f7d15840d052ec4e68c7244da9822a7618ed2f6b838f191946bafa74f4f859c230b5a7ea3123c24e0f4862405f1582f3c613d6e54bb000c0ad69679389ed9e");
    }
}