package my.application.ffmpeg;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FFmpegOption {
    INPUT("-i"),
    CODEC("-codec"),
    START_NUMBER("-start_number"),
    HLS_TIME("-HLS_TIME"),
    HLS_LIST_SIZE("-HLS_LIST_SIZE"),
    FILE("-f");
    private final String option;
}
