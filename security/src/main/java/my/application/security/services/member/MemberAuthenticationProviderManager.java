package my.application.security.services.member;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

@Slf4j
public class MemberAuthenticationProviderManager extends ProviderManager {

    public MemberAuthenticationProviderManager(AuthenticationProvider provider) {
        super(provider);
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Authentication authentication1 = null;
        for (AuthenticationProvider provider : super.getProviders()) {
            authentication1 = provider.authenticate(authentication);
        }
        return authentication1;
    }
}
