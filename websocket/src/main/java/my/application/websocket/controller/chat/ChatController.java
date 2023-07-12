package my.application.websocket.controller.chat;

import my.application.websocket.entities.chat.RoomEnter;
import my.application.websocket.entities.chat.TalkMessage;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
public class ChatController {

    @MessageMapping("/chat/room/{roomNumber}/enter")
    @SendTo("/chat/room/{roomNumber}/enter-notice")
    public RoomEnter chatRoomEnter(String id, @DestinationVariable("roomNumber") Integer roomNumber) {
        return new RoomEnter(id, roomNumber);
    }
//    @MessageMapping("/chat/room/{roomNumber}/talk")
//    @SendTo("/chat/room/{roomNumber}/new-message")
//    public TalkMessage chatRoomTalk(TalkMessage message, @DestinationVariable("roomNumber") Integer roomNumber) {
//        return message;
//    }

    @MessageMapping("/chat/room/{roomNumber}/talk")
    @SendToUser("/chat/room/{roomNumber}/new-message")
    public TalkMessage chatRoomTalk(TalkMessage message, @DestinationVariable("roomNumber") Integer roomNumber) {
        return message;
    }

}
