package my.application.security.filter.manager;

import lombok.extern.slf4j.Slf4j;
import my.application.security.filter.authority.MemberAuthority;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

@Slf4j
@Component
public class MemberAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext> {
    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, RequestAuthorizationContext object) {
        log.error("authorization manager check");
        return new AuthorizationDecision(authentication.get().getAuthorities().contains(MemberAuthority.MemberUserRoleAuthority.getInstance()));
    }

    @Override
    public void verify(Supplier<Authentication> authentication, RequestAuthorizationContext object) {
        log.error("authorization verify");
        AuthorizationManager.super.verify(authentication, object);
    }
}
