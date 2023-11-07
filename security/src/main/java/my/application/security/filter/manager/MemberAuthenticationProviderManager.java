package my.application.security.filter.manager;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Slf4j
public class MemberAuthenticationProviderManager extends ProviderManager {

    public MemberAuthenticationProviderManager(AuthenticationProvider provider) {
        super(provider);
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Authentication authentication1 = null;
        for (AuthenticationProvider provider : super.getProviders()) {
            if (authentication.getPrincipal() != null && provider.supports(authentication.getPrincipal().getClass())) {
                authentication1 = provider.authenticate(authentication);
            }
        }
        return authentication1;
    }
}
