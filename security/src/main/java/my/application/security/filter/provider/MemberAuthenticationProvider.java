package my.application.security.filter.provider;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.application.security.filter.exception.AuthenticationExternalDataErrorException;
import my.application.security.services.member.MemberSignInUserDetailService;
import my.application.security.services.member.MemberSignInUserDetails;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.security.auth.login.LoginException;

@Slf4j
@Component
@RequiredArgsConstructor
public class MemberAuthenticationProvider implements AuthenticationProvider {

    private final MemberSignInUserDetailService memberSignInUserDetailService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(MemberSignInUserDetails.class);
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        MemberSignInUserDetails principal = (MemberSignInUserDetails) authentication.getPrincipal();
        try {
            UserDetails userDetails = memberSignInUserDetailService.loadUserByUsername(principal.getUsername());
            String inputPassword = passwordEncoder.encode(principal.getPassword());
            if (inputPassword.equals(userDetails.getPassword())) {
                if (userDetails.isEnabled()) {
                    return new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
                } else {
                    throw new AccountExpiredException("account problem");
                }
            } else {
                throw new BadCredentialsException("bad Credential");
            }
        } catch (AuthenticationException e) {
            log.error("authentication error : {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("any other error : {}", e.getMessage());
            throw e;
        }
    }
}
