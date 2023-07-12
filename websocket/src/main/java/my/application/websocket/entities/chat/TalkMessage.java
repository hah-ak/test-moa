package my.application.websocket.entities.chat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TalkMessage {

    private Object message;
    private String id;
}
