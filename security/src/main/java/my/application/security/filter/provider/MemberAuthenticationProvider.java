package my.application.security.filter.provider;

import lombok.extern.slf4j.Slf4j;
import my.application.security.filter.exception.MemberNotEnabledAuthenticationException;
import my.application.security.services.member.MemberSignInUserDetailService;
import my.application.security.services.member.MemberSignInUserDetails;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

@Slf4j
public class MemberAuthenticationProvider implements AuthenticationProvider {

    private final MemberSignInUserDetailService memberSignInUserDetailService;

    public MemberAuthenticationProvider(MemberSignInUserDetailService memberSignInUserDetailService) {
        this.memberSignInUserDetailService = memberSignInUserDetailService;
    }

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
            throw new MemberNotEnabledAuthenticationException("not enabled");
        }

    }
}
