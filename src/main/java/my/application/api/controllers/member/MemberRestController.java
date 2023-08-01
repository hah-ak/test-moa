package my.application.api.controllers.member;

import lombok.RequiredArgsConstructor;
import my.application.api.services.member.MemberCRUDService;
import my.domain.mysql.entities.MemberEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/member")
public class MemberRestController {

    private final MemberCRUDService memberCRUDService;

    @PostMapping("/create")
    public MemberEntity create(MemberEntity member) {
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

    @PostMapping("/login")
    public MemberEntity login(@RequestParam(name = "id") String id, @RequestParam(name = "password") String password) {
        return memberCRUDService.login(id, password);
    }

}
