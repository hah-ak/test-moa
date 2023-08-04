package my.application.security.controllers;

import lombok.RequiredArgsConstructor;
import my.application.security.model.login.UserLogin;
import my.application.security.services.member.MemberService;
import my.domain.mysql.entities.MemberEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MainControllers {

    private final MemberService memberService;
    @GetMapping("/login-process")
    public MemberEntity loginProcess(@ModelAttribute UserLogin login) {
        return memberService.login(login);
    }


}
