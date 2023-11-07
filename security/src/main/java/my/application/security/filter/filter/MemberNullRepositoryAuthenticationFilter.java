package my.application.security.filter.filter;

import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import my.application.security.services.member.MemberSignInUserDetails;
import my.domain.mysql.entities.MemberEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

public class MemberNullRepositoryAuthenticationFilter extends GenericFilterBean {

        private final SecurityContextHolderStrategy securityContextHolderStrategy = SecurityContextHolder.getContextHolderStrategy();

        @Override
        public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
            String header = ((HttpServletRequest) request).getHeader("MY-APP-CREDENTIAL");
            MemberSignInUserDetails memberSignInUserDetails = new MemberSignInUserDetails(new MemberEntity("asdf@asdf.asdf","kim","1234",null));
//            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(memberSignInUserDetails, null, memberSignInUserDetails.getAuthorities());
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(null, null);
            SecurityContext context = securityContextHolderStrategy.getDeferredContext().get();
            if ( context == null) {
                context = securityContextHolderStrategy.createEmptyContext();
            }

            if ( usernamePasswordAuthenticationToken.isAuthenticated()) {
                context.setAuthentication(usernamePasswordAuthenticationToken);
            }
            chain.doFilter(request, response);
        }
}
