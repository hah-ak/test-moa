package my.application.streaming.services;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class FileConvertTest {

    FileConvert fileConvert = new FileConvert();

    @BeforeAll
    static void befor() {
        new FileConvert().createDefaultDir();
    }
    @Test
    void convertFile() throws IOException {
        fileConvert.convertFile("C:\\Users\\PC\\Desktop\\videos\\개웃김.mp4");
    }
}