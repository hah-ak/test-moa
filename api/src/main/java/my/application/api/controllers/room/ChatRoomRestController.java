package my.application.api.controllers.room;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.application.api.dto.member.MemberDTO;
import my.application.api.services.room.RoomService;
import my.application.api.entities.mysql.MemberJoinRoomEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.*;

@Slf4j
//@RestController
@RequiredArgsConstructor
//@RequestMapping("/chat-room")
public class ChatRoomRestController {

    private final RoomService roomService;
//    @PostMapping("/room")
    @KafkaListener(topics = "create", groupId = "group_1")
    public MemberJoinRoomEntity create(MemberDTO member) {
        return roomService.saveMemberJoinRoom(roomService.createRoom(), member);
    }

//    @GetMapping("/room/{roomId}")
    @KafkaListener(topics = "join", groupId = "group_1")
    public MemberJoinRoomEntity join(MemberDTO memberDTO, Integer roomId) {
         return roomService.findRoomById(memberDTO, roomId);
    }
}
