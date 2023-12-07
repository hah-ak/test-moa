package my.application.security.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import my.application.security.cookie.CookieUtils;
import my.application.security.dto.signUp.SignUp;
import my.application.security.services.member.MemberSecurityService;
import my.application.security.services.member.MemberSignInUserDetails;
import my.domain.mysql.entities.MemberEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sign-in")
public class MainControllers {

    private final MemberSecurityService memberService;
    @PostMapping("/sign-in-process")
    public MemberSignInUserDetails loginProcess(HttpServletRequest request, HttpServletResponse response) {

        CookieUtils.createLoginCookie(request, response);

        var auth = (Authentication) request.getUserPrincipal();
        return (MemberSignInUserDetails) auth.getPrincipal();
    }

    @PostMapping("/sign-up-process")
    public MemberEntity signUpProcess(@ModelAttribute SignUp signUp) {
        return memberService.signUpProcess(signUp);
    }

}
