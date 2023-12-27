package my.application.api.controllers.member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.application.api.services.member.MemberService;
import my.domain.mysql.entities.MemberEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/member")
public class MemberRestController {

    private final MemberService memberService;
    private final KafkaTemplate<String, MemberEntity> kafkaTemplate;

    @GetMapping("")
    public MemberEntity getMember(@RequestParam(name = "mem_no") Integer memNo) {
        return memberService.getMember(memNo);
    }
    @GetMapping("/members/permit-all")
    public List<MemberEntity> memberEntities() {
        return memberService.memberEntities();
    }

    @SendTo
    @KafkaListener(topics = "member.request.data", groupId = "group_1")
    public MemberEntity getMemberEntity(String userId) {
        return memberService.getMember(userId);

    }
}
