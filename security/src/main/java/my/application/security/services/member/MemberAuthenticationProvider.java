package my.application.security.services.member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Slf4j
public class MemberAuthenticationProvider implements AuthenticationProvider {

    private final MemberSignInUserDetailService memberSignInUserDetailService;

    public MemberAuthenticationProvider(MemberSignInUserDetailService memberSignInUserDetailService) {
        this.memberSignInUserDetailService = memberSignInUserDetailService;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        log.error("just supports");
        return false;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        log.error("just pass authenticate");

        return null;
    }
}
