package my.application.streaming.services;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class FileConvertTest {

    FileConvert fileConvert = new FileConvert();
    @Test
    void convertFile() throws IOException {
        fileConvert.convertFile();
    }
}