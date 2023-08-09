package my.application.security.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import my.application.security.cookie.CookieUtils;
import my.application.security.model.signIn.SignIn;
import my.application.security.model.signUp.SignUp;
import my.application.security.services.member.MemberSecurityService;
import my.application.security.services.member.MemberSignInUserDetails;
import my.domain.mysql.entities.MemberEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sign-in")
public class MainControllers {

    private final MemberSecurityService memberService;
    @PostMapping("/sign-in-process")
    public MemberSignInUserDetails loginProcess(@ModelAttribute SignIn signIn, HttpServletRequest request, HttpServletResponse response) {
        try {
            request.login(signIn.getId(), signIn.getPassword());
            request.getSession().setMaxInactiveInterval(60*30);
            CookieUtils.createLoginCookie(request, response);
        } catch (ServletException e) {
            throw new RuntimeException(e);
        }

        var auth = (Authentication) request.getUserPrincipal();
        return (MemberSignInUserDetails) auth.getPrincipal();
    }

    @PostMapping("/sign-up-process")
    public MemberEntity signUpProcess(@ModelAttribute SignUp signUp) {
        return memberService.signUpProcess(signUp);
    }

}
