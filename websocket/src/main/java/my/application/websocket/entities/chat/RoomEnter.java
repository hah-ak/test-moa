package my.application.websocket.entities.chat;

import lombok.Builder;
import lombok.Getter;

@Builder
public class RoomEnter {
    private String id;
    private Integer roomNumber;
}
