package my.application.ffmpeg;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum VideoCodec {
    StreamSpecifier(":v"),
    H264("h264");
    private String codec;
}
