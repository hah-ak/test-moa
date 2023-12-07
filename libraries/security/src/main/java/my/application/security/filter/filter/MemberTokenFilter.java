package my.application.security.filter.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.application.security.dto.token.MemberLoginToken;
import my.application.security.resolvers.MemberResolver;
import my.domain.mysql.entities.MemberEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class MemberTokenFilter extends OncePerRequestFilter {

    private final SecurityContextHolderStrategy securityContextHolderStrategy = SecurityContextHolder.getContextHolderStrategy();
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        MemberLoginToken memberLoginToken = MemberResolver.resolveArgument(request);

        if (memberLoginToken == null) {

        }
        else {
            memberLoginToken.id();
            memberLoginToken.password();
            memberLoginToken.session();
        }

        filterChain.doFilter(request, response);

    }
}
