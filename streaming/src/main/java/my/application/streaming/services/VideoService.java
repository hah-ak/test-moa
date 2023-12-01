package my.application.streaming.services;

import java.nio.file.Path;
import java.nio.file.Paths;

public interface VideoService {
    String WINDOW_SEHLL = "C:\\Windows\\System32\\WindowsPowerShell\\v1.0\\powershell.exe";
    String DEFAULT_SHELL = "/bin/sh";
    String VIDEO_PATH = "/video";
    String WINDOW_COMMAND_OPTION = "/c";
    String DEFAULT_COMMAND_OPTION = "-c";
    Path HOME = Paths.get(System.getProperty("user.home")).toAbsolutePath();
}
