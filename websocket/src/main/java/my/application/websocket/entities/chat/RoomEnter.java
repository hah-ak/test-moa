package my.application.websocket.entities.chat;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoomEnter {

    public RoomEnter(String id, Integer roomNumber) {
        this.id = id;
        this.roomNumber = roomNumber;

    }

    private String id;
    private Integer roomNumber;
}
