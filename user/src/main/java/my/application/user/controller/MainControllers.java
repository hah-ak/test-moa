package my.application.user.controller;

import com.google.common.net.HttpHeaders;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.SignedJWT;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import my.application.user.dto.signIn.SignIn;
import my.application.user.dto.signUp.SignUp;
import my.application.user.entities.mysql.MemberEntity;
import my.application.user.entities.redis.LoginSessionEntity;
import my.application.user.services.member.MemberSecurityService;
import my.application.user.services.member.MemberSignInUserDetails;
import my.application.user.util.JWTUtil;
import my.domain.redis.RedisCommonTemplate;
import my.domain.redis.RedisSessionTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sign-in")
public class MainControllers {

    private final MemberSecurityService memberService;
    private final RedisSessionTemplate<String, LoginSessionEntity> redisSessionTemplate;
    private final SecurityContext context = SecurityContextHolder.getContext();

    @PostMapping("/sign-in-process")
    public void loginProcess(HttpServletResponse response, @AuthenticationPrincipal MemberSignInUserDetails detail) throws JOSEException, ParseException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        WebAuthenticationDetails details = (WebAuthenticationDetails) authentication.getDetails();
        SignedJWT jws = JWTUtil.createJWS(detail, details.getSessionId());
        response.addHeader("MY_APP_TOKEN", jws.serialize());
        redisSessionTemplate.opsForValue().setIfAbsent(
            jws.getJWTClaimsSet().getJWTID(),
            new LoginSessionEntity(
                    jws.getJWTClaimsSet().getJWTID(),
                    jws.getSignature().toString(),
                    detail.getUsername(),
                    details.getRemoteAddress(),
                    LocalDateTime.now().atZone(ZoneId.of("UTC")).toLocalDateTime()
            ),
            Duration.ofSeconds(30*60)
        );
    }

    @PostMapping("/sign-up-process")
    public MemberEntity signUpProcess(@ModelAttribute SignUp signUp) {
        return memberService.signUpProcess(signUp);
    }

}
