package my.application.chat.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import my.application.chat.entities.mysql.RoomEntity;
import my.application.chat.entities.mysql.RoomJoinMemberEntity;
import my.application.chat.repositories.mysql.RoomJoinMemberRepository;
import my.application.chat.repositories.mysql.RoomRepository;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;

@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChattingController {

    private final RoomRepository roomRepository;
    private final RoomJoinMemberRepository roomJoinMemberRepository;
    private final RedisTemplate<String,String> redisTemplate;

    @PostMapping("/create")
    public Long start(HttpServletRequest request, @RequestBody HashMap<String, Object> roomParam) {
        RoomEntity save = roomRepository.save(new RoomEntity((Integer) roomParam.get("maxUser"), (String) roomParam.get("type")));
        long memberNumber = Long.parseLong(request.getHeader("MEMBER_NUMBER"));
        RoomJoinMemberEntity save1 = roomJoinMemberRepository.save(new RoomJoinMemberEntity(save, memberNumber));

        return save1.getRoom().getId();
    }

}
