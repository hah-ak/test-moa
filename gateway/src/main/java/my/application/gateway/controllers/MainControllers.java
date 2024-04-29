package my.application.gateway.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import my.application.gateway.cookie.CookieUtils;
import my.application.gateway.dto.signUp.SignUp;
import my.application.gateway.entities.mysql.member.MemberEntity;
import my.application.gateway.services.member.MemberSecurityService;
import my.application.gateway.services.member.MemberSignInUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
        return (MemberSignInUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @PostMapping("/sign-up-process")
    public MemberEntity signUpProcess(@ModelAttribute SignUp signUp) {
        return memberService.signUpProcess(signUp);
    }

}
