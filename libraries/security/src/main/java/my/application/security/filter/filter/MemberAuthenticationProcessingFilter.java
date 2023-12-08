package my.application.security.filter.filter;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import my.application.security.dto.signIn.SignIn;
import my.application.security.filter.handlers.MemberAuthenticationEntryPoint;
import my.application.security.filter.manager.MemberAuthenticationProcessingProviderManager;
import my.application.security.services.member.MemberSignInUserDetails;
import my.domain.mysql.entities.MemberAuthorityEntity;
import my.domain.mysql.entities.MemberEntity;
import my.domain.mysql.repositories.member.MemberAuthorityRepository;
import my.domain.mysql.repositories.member.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.MethodNotAllowedException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// 인증시도 시에 (로그인 시) 타게되는 필터. ( authenticationEntryPoint 를 통해 들어오거나 직접 로그인하거나 등등)
@Slf4j
@Component
public class MemberAuthenticationProcessingFilter extends AbstractAuthenticationProcessingFilter {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;
    private final MemberAuthorityRepository memberAuthorityRepository;

    @Autowired
    public MemberAuthenticationProcessingFilter(
            MemberAuthenticationProcessingProviderManager memberAuthenticationProcessingProviderManager,
            PasswordEncoder passwordEncoder,
            MemberRepository memberRepository,
            MemberAuthorityRepository memberAuthorityRepository
    ) {
        super(MemberAuthenticationEntryPoint.SIGN_IN_URL, memberAuthenticationProcessingProviderManager);
        super.setContinueChainBeforeSuccessfulAuthentication(true);
        this.passwordEncoder = passwordEncoder;
        this.memberRepository = memberRepository;
        this.memberAuthorityRepository = memberAuthorityRepository;

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
            MemberEntity member = memberRepository.findById(signIn.id());

            boolean matches = passwordEncoder.matches(signIn.password(), member.getPassword());
            if (!matches) {
                throw new BadCredentialsException("invalid password");
            }

            MemberSignInUserDetails memberSignInUserDetails = new MemberSignInUserDetails(member);

            usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(memberSignInUserDetails, signIn.password(), memberSignInUserDetails.getAuthorities());
            usernamePasswordAuthenticationToken.setDetails("ip주소등등을 넣자");


        } catch (Exception e) {
            log.error("parsing error : {}", e.getMessage());
        }

        return this.getAuthenticationManager().authenticate(usernamePasswordAuthenticationToken);
    }
}
