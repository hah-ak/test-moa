package my.application.ffmpeg;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class Codec {

    @AllArgsConstructor
    @Getter
    public enum Video{
        StreamSpecifier(":v"),
        H264("h264"),
        H265("h265");
        private final String codec;
    }
    @AllArgsConstructor
    @Getter
    public enum Audio {
        StreamSpecifier(":a"),
        AC3("ac3");
        private final String codec;
    }
}
