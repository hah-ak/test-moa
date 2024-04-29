package my.application.gateway.filter.manager;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.application.gateway.filter.authority.MemberRoleHierarchy;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

@Slf4j
@Component
@RequiredArgsConstructor
public class MemberAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext> {

    private final MemberRoleHierarchy memberRoleHierarchy;
    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, RequestAuthorizationContext object) {
        log.error("authorization manager check");

        memberRoleHierarchy.getReachableGrantedAuthorities(authentication.get().getAuthorities())
        return new AuthorizationDecision(authentication.get().getAuthorities().contains(memberUserRoleAuthority));
    }

    @Override
    public void verify(Supplier<Authentication> authentication, RequestAuthorizationContext object) {
        log.error("authorization verify");
        AuthorizationManager.super.verify(authentication, object);
    }
}
