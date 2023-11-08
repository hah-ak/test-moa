package my.application.security.filter.filter;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import my.application.security.filter.exception.AuthenticationExternalDataErrorException;
import my.application.security.filter.manager.MemberAuthenticationProviderManager;
import my.application.security.services.member.MemberSignInUserDetails;
import my.domain.mysql.entities.MemberEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import java.io.IOException;

@Slf4j
public class MemberAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    public MemberAuthenticationFilter(String defaultFilterProcessesUrl, MemberAuthenticationProviderManager memberAuthenticationProviderManager) {
        super(defaultFilterProcessesUrl, memberAuthenticationProviderManager);
        super.setContinueChainBeforeSuccessfulAuthentication(true);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
            String header = request.getHeader("MY-APP-CREDENTIAL");
            MemberSignInUserDetails memberSignInUserDetails = new MemberSignInUserDetails(new MemberEntity("asdf@asdf.asdf","kim","1234",null));
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(memberSignInUserDetails, "header", memberSignInUserDetails.getAuthorities());
            usernamePasswordAuthenticationToken.setDetails("ip주소등등을 넣자");


            return this.getAuthenticationManager().authenticate(usernamePasswordAuthenticationToken);
    }
}
