package my.application.api.controllers.room;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.application.api.services.member.MemberCRUDService;
import my.application.api.services.room.RoomService;
import my.domain.mysql.entities.MemberJoinRoomEntity;
import my.domain.mysql.entities.RoomEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/chat-room")
public class ChatRoomRestController {

    private final RoomService roomService;
    private final MemberCRUDService memberCRUDService;
    @PostMapping("/room")
    public MemberJoinRoomEntity create(@RequestParam(name = "mem-no") Integer memNo) {
        return roomService.saveMemberJoinRoom(roomService.createRoom(), memberCRUDService.getMember(memNo));
    }

    @GetMapping("/room/{roomId}")
    public MemberJoinRoomEntity join(@PathVariable(name = "roomId") String roomId) {
         return roomService.findRoomById(roomId);
    }
}
