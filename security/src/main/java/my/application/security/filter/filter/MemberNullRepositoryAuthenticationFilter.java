package my.application.security.filter.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.application.security.filter.manager.MemberAuthenticationProcessingProviderManager;
import my.application.security.resolvers.MemberResolver;
import my.application.security.services.member.MemberSignInUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class MemberNullRepositoryAuthenticationFilter extends GenericFilterBean {

    // 필터를 완전 커스터 마이징. authentication 을 만들어 준다.
    private final SecurityContextHolderStrategy securityContextHolderStrategy = SecurityContextHolder.getContextHolderStrategy();
    private final MemberAuthenticationProcessingProviderManager memberAuthenticationProcessingProviderManager;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//        MemberSignInUserDetails memberSignInUserDetails = new MemberSignInUserDetails(new MemberEntity("asdf@asdf.asdf", "kim", "1234", null));
        MemberSignInUserDetails memberSignInUserDetails = new MemberSignInUserDetails(MemberResolver.resolveArgument((HttpServletRequest) request));
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                memberSignInUserDetails, null, memberSignInUserDetails.getAuthorities()
        );
        SecurityContext context = securityContextHolderStrategy.getDeferredContext().get();
        if (context == null) {
            context = securityContextHolderStrategy.createEmptyContext();
        }

        Authentication authenticate = memberAuthenticationProcessingProviderManager.authenticate(usernamePasswordAuthenticationToken);
        if (authenticate.isAuthenticated()) {
            context.setAuthentication(usernamePasswordAuthenticationToken);
        }
        chain.doFilter(request, response);
    }
}
