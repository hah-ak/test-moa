package my.application.api.controllers.member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.application.api.services.member.MemberService;
import my.domain.mysql.entities.MemberEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/member")
public class MemberRestController {

    private final MemberService memberService;

    @GetMapping("")
    public MemberEntity getMember(@RequestParam(name = "mem_no") Integer memNo) {
        return memberService.getMember(memNo);
    }
    @GetMapping("/members/permit-all")
    public List<MemberEntity> memberEntities() {
        return memberService.memberEntities();
    }
}
