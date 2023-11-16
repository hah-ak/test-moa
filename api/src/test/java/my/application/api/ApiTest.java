package my.application.api;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class ApiTest {

    @Test
    void testt() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();


        System.out.println(bCryptPasswordEncoder.matches("absd",bCryptPasswordEncoder.encode("absd")));
    }

    @Test
    void aweifj() {
        System.out.println(System.getProperty("user.home"));
    }

    @Test
    void cmdTest() {
        new ProcessBuilder("bash","echo","test");
    }
}
