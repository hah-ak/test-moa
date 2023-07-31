package my.application.myApp.entities.first;

import lombok.Getter;

@Getter
public class HelloMessage {
    private String name;
    public HelloMessage(String name) {
        this.name = name;
    }
}
