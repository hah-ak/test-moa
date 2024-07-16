package my.application.member.controller;

import com.google.common.net.HttpHeaders;
import com.nimbusds.jose.JOSEException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import my.application.member.dto.signUp.SignUp;
import my.application.member.entities.mysql.MemberEntity;
import my.application.member.services.member.MemberSecurityService;
import my.application.member.services.member.MemberSignInUserDetails;
import my.application.member.util.JWTUtil;
import my.domain.redis.RedisCommonTemplate;
import my.domain.redis.RedisSessionTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sign-in")
public class MainControllers {

    private final MemberSecurityService memberService;
    private final RedisCommonTemplate<String, Map<String, Object>> redisTemplate;
    private final RedisSessionTemplate<String, Map<String, Object>> redisSessionTemplate;

    @PostMapping("/sign-in-process")
    public void loginProcess(HttpServletRequest request, HttpServletResponse response) throws JOSEException {
        MemberSignInUserDetails principal = (MemberSignInUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String jwsToken = JWTUtil.createJWSToken(principal);
        response.addHeader("MY_APP_TOKEN", jwsToken);
        Boolean b = redisSessionTemplate.opsForValue().setIfAbsent(principal.getUsername(), Map.of(
                        "SESSION", request.getRequestedSessionId(),
                        "NiCKNAME",principal.getUserNickName(),
                        "NUMBER", principal.getUserNumber(),
                        "LOGIN_TIME", LocalDateTime.now(),
                        "LOGIN_IP", request.getHeader(HttpHeaders.X_FORWARDED_FOR)
                )
        );
    }

    @PostMapping("/sign-up-process")
    public MemberEntity signUpProcess(@ModelAttribute SignUp signUp) {
        return memberService.signUpProcess(signUp);
    }

}
