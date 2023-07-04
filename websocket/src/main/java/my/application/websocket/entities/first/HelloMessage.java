package my.application.websocket.entities.first;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HelloMessage {

    public HelloMessage(String name) {
        this.name = name;
    }

    private String name;
}
