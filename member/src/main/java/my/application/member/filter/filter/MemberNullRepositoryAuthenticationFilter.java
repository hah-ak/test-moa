package my.application.member.filter.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.application.member.filter.manager.MemberAuthenticationProcessingProviderManager;
import my.application.member.services.member.MemberSignInUserDetails;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;

import java.io.IOException;

// session 이나 다른 저장소를 쓰지 않고, 오직 쿠키등을 쓰기위해 만들어본 클래스.
@Slf4j
//@Component
@RequiredArgsConstructor
public class MemberNullRepositoryAuthenticationFilter {
//        extends GenericFilterBean { // genericfilterbean과 같은 filter 클래스를 전부다 찾아서 빈으로 만들어 필터에 추가 하므로 조심할것.

    // 필터를 완전 커스터 마이징. authentication 을 만들어 준다.
    private final SecurityContextHolderStrategy securityContextHolderStrategy = SecurityContextHolder.getContextHolderStrategy();
    private final MemberAuthenticationProcessingProviderManager memberAuthenticationProcessingProviderManager;

//    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//        MemberSignInUserDetails memberSignInUserDetails = new MemberSignInUserDetails(new MemberEntity("asdf@asdf.asdf", "kim", "1234", null));
        MemberSignInUserDetails memberSignInUserDetails = null;
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
