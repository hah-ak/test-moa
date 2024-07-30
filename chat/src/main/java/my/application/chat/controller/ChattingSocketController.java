package my.application.chat.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import my.application.chat.entities.mysql.RoomEntity;
import my.application.chat.entities.mysql.RoomJoinMemberEntity;
import my.application.chat.repositories.mysql.RoomJoinMemberRepository;
import my.application.chat.repositories.mysql.RoomRepository;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.Map;
import java.util.Optional;

@Controller
@MessageMapping("/chat/pub")
@SendTo("/chat/sub")
@RequiredArgsConstructor
public class ChattingSocketController {

    private final RoomRepository roomRepository;
    private final RoomJoinMemberRepository roomJoinMemberRepository;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/room.enter.{roomID}")
    @SendTo("/room.{roomID}")
    public String enterRoom(@DestinationVariable Long roomID, HttpServletRequest request) {
        Optional<RoomEntity> byId = roomRepository.findById(roomID);
        Long memberNumber = Long.parseLong(request.getHeader("MEMBER_NUMBER"));
        String nickName = request.getHeader("NICKNAME");
        if (byId.isEmpty()) {
            return "존재하지 않는 방입니다.";
        }
        RoomEntity roomEntity = byId.get();
        for (RoomJoinMemberEntity member : byId.get().getJoinMembers()) {
            if (member.getMemberNumber().equals(memberNumber)) {
                return null;
            }
        }

        roomJoinMemberRepository.save(new RoomJoinMemberEntity(roomEntity, memberNumber));
        return nickName + "님이 대화방에 입장 하셨습니다.";
    }

    @MessageMapping("/message.{roomID}")
    public void message(@DestinationVariable Long roomID, @Payload Map<String,Object> data, HttpServletRequest request) {
        simpMessagingTemplate.convertAndSend("/sub/chat", data.get("message"));
    }

}
