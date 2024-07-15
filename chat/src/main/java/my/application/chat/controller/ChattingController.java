package my.application.chat.controller;

import lombok.RequiredArgsConstructor;
import my.application.chat.entities.mysql.RoomEntity;
import my.application.chat.entities.mysql.RoomJoinMemberEntity;
import my.application.chat.repositories.mysql.RoomJoinMemberRepository;
import my.application.chat.repositories.mysql.RoomRepository;
import my.domain.mysql.config.MySQLDefaultConfig;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.util.Optional;

@Controller
@MessageMapping("/pub/chat")
@SendTo("/sub/chat")
@RequiredArgsConstructor
public class ChattingController {

    private final RoomRepository roomRepository;
    private final RoomJoinMemberRepository roomJoinMemberRepository;
    @MessageMapping("/room.enter.{roomID}")
    @SendTo("/room.{roomID}")
    public String enterRoom(@DestinationVariable Long roomID, Principal principal) {
        Optional<RoomEntity> byId = roomRepository.findById(roomID);
        return "";
    }

}
