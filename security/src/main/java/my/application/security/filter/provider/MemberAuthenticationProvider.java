package my.application.security.filter.provider;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.application.security.filter.exception.AuthenticationExternalDataErrorException;
import my.application.security.services.member.MemberSignInUserDetailService;
import my.application.security.services.member.MemberSignInUserDetails;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MemberAuthenticationProvider implements AuthenticationProvider {

    private final MemberSignInUserDetailService memberSignInUserDetailService;

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(MemberSignInUserDetails.class);
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        MemberSignInUserDetails principal = (MemberSignInUserDetails) authentication.getPrincipal();
        UserDetails userDetails = memberSignInUserDetailService.loadUserByUsername(principal.getUsername());
        if (userDetails.isEnabled()) {
            return authentication;
        } else {
            throw new AccountExpiredException("not enabled");
        }

    }
}
