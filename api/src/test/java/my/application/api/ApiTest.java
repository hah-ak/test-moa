package my.application.api;

import org.junit.jupiter.api.Test;

public class ApiTest {

    @Test
    void aweifj() {
        System.out.println(System.getProperty("user.home"));
    }

    @Test
    void cmdTest() {
        new ProcessBuilder("bash","echo","test");
    }
}
