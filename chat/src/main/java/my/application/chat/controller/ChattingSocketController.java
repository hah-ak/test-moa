package my.application.chat.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import my.application.chat.dto.ChatSendDTO;
import my.application.chat.entities.mysql.RoomEntity;
import my.application.chat.entities.mysql.RoomJoinMemberEntity;
import my.application.chat.repositories.mysql.RoomJoinMemberRepository;
import my.application.chat.repositories.mysql.RoomRepository;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.Map;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class ChattingSocketController {

    private final RoomRepository roomRepository;
    private final RoomJoinMemberRepository roomJoinMemberRepository;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/room.enter.{roomID}")
    @SendTo("/chat/sub/room.{roomID}")
    public ChatSendDTO enterRoom(@DestinationVariable("roomID") Long roomID, @Headers Map<String, Object> headers) {
        Optional<RoomEntity> byId = roomRepository.findById(roomID);
        Long memberNumber = (Long) headers.get("MEMBER_NUMBER");
        String nickName = (String) headers.get("NICKNAME");
        if (byId.isEmpty()) {
            return new ChatSendDTO("System","존재하지 않는 방입니다.");
        }
        RoomEntity roomEntity = byId.get();
        for (RoomJoinMemberEntity member : byId.get().getJoinMembers()) {
            if (member.getMemberNumber().equals(memberNumber)) {
                return null;
            }
        }

        roomJoinMemberRepository.save(new RoomJoinMemberEntity(roomEntity, memberNumber));

        return new ChatSendDTO(nickName,nickName + "님이 대화방에 입장 하셨습니다.");
    }

    @MessageMapping("/message.{roomID}")
    public void message(@DestinationVariable("roomID") Long roomID, @Payload Map<String,Object> data,@Headers Map<String, Object> headers) {
        String nickName = (String) headers.get("NICKNAME");
        simpMessagingTemplate.convertAndSend("/chat/sub/message."+roomID, new ChatSendDTO(nickName, (String) data.get("content")));
    }

}
