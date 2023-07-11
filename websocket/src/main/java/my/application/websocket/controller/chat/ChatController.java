package my.application.websocket.controller.chat;

import my.application.websocket.entities.chat.RoomEnter;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
public class ChatController {

    @MessageMapping("/chat/room/{roomNumber}")
    @SendTo("/chat/room/{roomNumber}")
    public RoomEnter chatRoomEnter(String id, @DestinationVariable Integer roomNumber) {
        return RoomEnter.builder().roomNumber(roomNumber).id(id).build();
    }
}
