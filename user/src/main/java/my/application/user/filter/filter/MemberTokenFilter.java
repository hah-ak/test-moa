package my.application.user.filter.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.application.user.dto.token.MemberLoginToken;
import my.application.user.entities.mysql.MemberEntity;
import my.application.user.repositories.mysql.MemberRepository;
import my.application.user.resolvers.MemberResolver;
import my.application.user.services.member.MemberSignInUserDetails;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class MemberTokenFilter extends OncePerRequestFilter {

    private final PasswordEncoder passwordEncoder;
    private final SecurityContextHolderStrategy securityContextHolderStrategy = SecurityContextHolder.getContextHolderStrategy();
    private final MemberRepository memberRepository;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        MemberLoginToken memberLoginToken = MemberResolver.resolveArgument(request);
        if (memberLoginToken == null) {

        }
        else {
            SecurityContext context = securityContextHolderStrategy.getContext();
            MemberEntity member = memberRepository.findById(memberLoginToken.id());
            MemberSignInUserDetails memberSignInUserDetails = new MemberSignInUserDetails(member);
            context.setAuthentication(new UsernamePasswordAuthenticationToken(memberSignInUserDetails, memberLoginToken.password(), memberSignInUserDetails.getAuthorities()));
        }

        filterChain.doFilter(request, response);

    }
}
