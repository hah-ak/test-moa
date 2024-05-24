package my.application.api;

import my.application.api.services.study.Child;
import my.application.api.services.study.Parent;
import org.junit.jupiter.api.Test;

import java.util.List;

public class ApiTest {

    @Test
    void aweifj() {
        System.out.println(System.getProperty("user.home"));
    }

    @Test
    void cmdTest() {
        new ProcessBuilder("bash","echo","test");
    }

    @Test
    void whang() {
        List<Object> objects = List.of();
        objects.get(0);
        int a = 4;
        Child child = new Child();
        print(child);

    }

    void print(Parent parent) {
        parent.print();
    }
}
