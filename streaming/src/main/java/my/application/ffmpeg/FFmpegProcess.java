package my.application.ffmpeg;

public class FFmpegProcess {

    private String input;
    private String codec;
    private int startNumber;
    private int hlsTime;
    private int hlsListSize;
    private String file;
    private String output;

    private FFmpegProcess(String input, String output) {
        this.input = input;
        this.output = output;
    }

    public static class Builder {

        private FFmpegProcess fFmpegProcess;
        public Builder(String input, String output) {
            this.fFmpegProcess = new FFmpegProcess(input, output);
        }

    }
}
