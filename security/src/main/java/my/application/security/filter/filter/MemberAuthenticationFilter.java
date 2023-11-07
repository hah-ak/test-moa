package my.application.security.filter.filter;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import my.application.security.filter.manager.MemberAuthenticationProviderManager;
import my.application.security.services.member.MemberSignInUserDetails;
import my.domain.mysql.entities.MemberEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
public class MemberAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    public MemberAuthenticationFilter(String defaultFilterProcessesUrl, MemberAuthenticationProviderManager memberAuthenticationProviderManager) {
        super(defaultFilterProcessesUrl, memberAuthenticationProviderManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        String header = request.getHeader("MY-APP-CREDENTIAL");
        MemberSignInUserDetails memberSignInUserDetails = new MemberSignInUserDetails(new MemberEntity("asdf@asdf.asdf","kim","1234",null));

//            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(memberSignInUserDetails, null, memberSignInUserDetails.getAuthorities());
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(null, null);

        return this.getAuthenticationManager().authenticate(usernamePasswordAuthenticationToken);
    }
}
