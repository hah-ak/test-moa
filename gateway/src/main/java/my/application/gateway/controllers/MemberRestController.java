package my.application.gateway.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.application.gateway.entities.mysql.member.MemberEntity;
import my.application.gateway.services.member.MemberService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/member")
public class MemberRestController {

    private final MemberService memberService;
//    private final KafkaTemplate<String, MemberEntity> kafkaTemplate;

    @GetMapping("")
    public MemberEntity getMember(@RequestParam(name = "mem_no") Integer memNo) {
        return memberService.getMember(memNo);
    }
    @GetMapping("/members/permit-all")
    public List<MemberEntity> memberEntities() {
        return memberService.memberEntities();
    }

//    @SendTo
//    @KafkaListener(topics = "member.request.data", groupId = "group_1")
//    public MemberEntity getMemberEntity(String userId) {
//        return memberService.getMember(userId);
//    }
}
