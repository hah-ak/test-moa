package my.application.security.controllers;

import lombok.RequiredArgsConstructor;
import my.application.security.model.signIn.UserLogin;
import my.application.security.model.signUp.SignUp;
import my.application.security.services.member.MemberService2;
import my.domain.mysql.entities.MemberEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MainControllers {

    private final MemberService2 memberService;
    @PostMapping("/sign-in-process")
    public MemberEntity loginProcess(@ModelAttribute UserLogin login) {
        return memberService.login(login);
    }

    @PostMapping("/sign-up-process")
    public MemberEntity signUpProcess(@ModelAttribute SignUp signUp) {
        return memberService.signUpProcess(signUp);
    }

}
