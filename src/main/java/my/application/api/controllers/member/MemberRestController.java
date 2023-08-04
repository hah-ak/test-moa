package my.application.api.controllers.member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.application.api.dto.member.SignUp;
import my.application.api.services.member.MemberCRUDService;
import my.domain.mysql.entities.MemberEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/member")
public class MemberRestController {

    private final MemberCRUDService memberCRUDService;

    @PostMapping("/create")
    public boolean create(@ModelAttribute SignUp member) {
        return memberCRUDService.createMember(member);
    }

    @GetMapping("/members")
    public List<MemberEntity> memberEntities() {
        return memberCRUDService.memberEntities();
    }

    @GetMapping("/member")
    public MemberEntity getMember(@RequestParam(name = "mem_no") Integer memNo) {
        return memberCRUDService.getMember(memNo);
    }
}
