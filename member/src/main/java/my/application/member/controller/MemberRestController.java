package my.application.member.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.application.member.entities.mysql.MemberEntity;
import my.application.member.services.member.MemberService;
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

    @GetMapping("")
    public MemberEntity getMember(@RequestParam(name = "mem_no") Long memNo) {
        return memberService.getMember(memNo);
    }
    @GetMapping("/members/permit-all")
    public List<MemberEntity> memberEntities() {
        return memberService.memberEntities();
    }
}
