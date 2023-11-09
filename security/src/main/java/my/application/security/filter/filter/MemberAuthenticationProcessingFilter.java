package my.application.security.filter.filter;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import my.application.security.entities.signIn.SignIn;
import my.application.security.filter.handlers.MemberAuthenticationEntryPoint;
import my.application.security.filter.manager.MemberAuthenticationProcessingProviderManager;
import my.application.security.services.member.MemberSignInUserDetails;
import my.domain.mysql.entities.MemberEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.MethodNotAllowedException;

import java.io.IOException;
import java.util.List;

@Slf4j
public class MemberAuthenticationProcessingFilter extends AbstractAuthenticationProcessingFilter {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public MemberAuthenticationProcessingFilter(
            MemberAuthenticationProcessingProviderManager memberAuthenticationProcessingProviderManager
    ) {
        super(MemberAuthenticationEntryPoint.SIGN_IN_URL, memberAuthenticationProcessingProviderManager);
        super.setContinueChainBeforeSuccessfulAuthentication(true);


        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        if (!request.getMethod().equals(HttpMethod.POST.name())) {
            throw new MethodNotAllowedException(request.getMethod(), List.of(HttpMethod.POST));
        }
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = null;
        try {
            SignIn signIn = this.objectMapper.readValue(request.getReader(), SignIn.class);
            MemberSignInUserDetails memberSignInUserDetails = new MemberSignInUserDetails(new MemberEntity(signIn.id(), null, signIn.password(), null));

            usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(memberSignInUserDetails, signIn.password(), memberSignInUserDetails.getAuthorities());
            usernamePasswordAuthenticationToken.setDetails("ip주소등등을 넣자");
        } catch (Exception e) {
            log.error("parsing error : {}", e.getMessage());
        }

        return this.getAuthenticationManager().authenticate(usernamePasswordAuthenticationToken);
    }
}
