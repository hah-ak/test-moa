package my.application.api.controllers.member;

import lombok.RequiredArgsConstructor;
import my.application.api.entities.mysql.MemberEntity;
import my.application.api.services.member.MemberCRUDService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
