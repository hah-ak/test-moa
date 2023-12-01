package my.application.ffmpeg;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum AudioCodec {
    StreamSpecifier(":a"),
    AC3("ac3");
    private String codec;
}
