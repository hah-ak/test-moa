package my.application.user.filter.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.application.user.dto.token.MemberLoginToken;
import my.application.user.entities.redis.LoginSessionEntity;
import my.application.user.filter.exception.AuthenticationExternalDataErrorException;
import my.application.user.resolvers.MemberResolver;
import my.application.user.services.member.MemberSignInUserDetails;
import my.domain.redis.RedisSessionTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;

@Slf4j
public class MemberTokenFilter extends OncePerRequestFilter {

    private final SecurityContextHolderStrategy securityContextHolderStrategy = SecurityContextHolder.getContextHolderStrategy();
    private final RedisSessionTemplate<String, LoginSessionEntity> redisSessionTemplate;

    public MemberTokenFilter(RedisSessionTemplate<String, LoginSessionEntity> redisSessionTemplate) {
        this.redisSessionTemplate = redisSessionTemplate;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            MemberLoginToken memberLoginToken = MemberResolver.resolveArgument(request);

            LoginSessionEntity loginSessionEntity = redisSessionTemplate.opsForValue().get(memberLoginToken.session());


            SecurityContext context = securityContextHolderStrategy.getContext();
            MemberSignInUserDetails memberSignInUserDetails = new MemberSignInUserDetails(memberLoginToken.email(), memberLoginToken.name(), null,new ArrayList<>());

            context.setAuthentication(new UsernamePasswordAuthenticationToken(memberSignInUserDetails, null, memberSignInUserDetails.getAuthorities()));

            filterChain.doFilter(request, response);
        } catch (Exception e) {
            filterChain.doFilter(request, response);
        }

    }
}
