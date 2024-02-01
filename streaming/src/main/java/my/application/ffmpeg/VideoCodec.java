package my.application.ffmpeg;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum VideoCodec {
    StreamSpecifier(":v"),
    H264("h264"),
    H265("h265");
    private String codec;
}
