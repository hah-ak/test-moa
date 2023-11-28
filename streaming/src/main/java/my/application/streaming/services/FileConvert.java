package my.application.streaming.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@Service
public class FileConvert {
    private final String window = "C:\\Windows\\System32\\WindowsPowerShell\\v1.0\\powershell.exe ";
    private final String defaultShell = "/bin/sh ";
    private final String shell;
    public FileConvert() {
        shell = System.getProperty("os.name").contains("window") ? window : defaultShell;

    }
    public void convertFile() throws IOException {
        Process exec = Runtime.getRuntime().exec(shell + "mkdir .\\Desktop\\javaTtest");

        Runtime.getRuntime().exec(shell + "ffmpeg");

    }
}
