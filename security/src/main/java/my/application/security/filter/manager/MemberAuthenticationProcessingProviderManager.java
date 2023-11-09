package my.application.security.filter.manager;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MemberAuthenticationProcessingProviderManager extends ProviderManager {

    @Autowired
    public MemberAuthenticationProcessingProviderManager(AuthenticationProvider... providers) {
        super(providers);
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Authentication authentication1 = null;
        for (AuthenticationProvider provider : super.getProviders()) {
            if (provider.supports(authentication.getPrincipal().getClass())) {
                authentication1 = provider.authenticate(authentication);
            }
        }
        return authentication1;
    }
}
