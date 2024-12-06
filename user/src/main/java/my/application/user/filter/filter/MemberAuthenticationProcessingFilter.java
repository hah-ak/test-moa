package my.application.user.filter.filter;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import my.application.user.dto.signIn.SignIn;
import my.application.user.entities.mysql.MemberEntity;
import my.application.user.filter.handlers.MemberAuthenticationEntryPoint;
import my.application.user.filter.manager.MemberAuthenticationProcessingProviderManager;
import my.application.user.repositories.mysql.MemberRepository;
import my.application.user.services.member.MemberSignInUserDetails;
import my.domain.redis.RedisSessionTemplate;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.io.IOException;
import java.util.ArrayList;

// 인증시도 시에 (로그인 시) 타게되는 필터. ( authenticationEntryPoint 를 통해 들어오거나 직접 로그인하거나 등등)
@Slf4j
//@Component
public class MemberAuthenticationProcessingFilter extends AbstractAuthenticationProcessingFilter {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;

//    @Autowired
    public MemberAuthenticationProcessingFilter(
            MemberAuthenticationProcessingProviderManager memberAuthenticationProcessingProviderManager,
            PasswordEncoder passwordEncoder,
            MemberRepository memberRepository,
            RedisSessionTemplate<String, Object> redisSessionTemplate
    ) {
        super(new AntPathRequestMatcher(MemberAuthenticationEntryPoint.SIGN_IN_URL + "**", "POST"), memberAuthenticationProcessingProviderManager);
        super.setContinueChainBeforeSuccessfulAuthentication(true);
        this.passwordEncoder = passwordEncoder;
        this.memberRepository = memberRepository;

        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = null;
        try {
            SignIn signIn = this.objectMapper.readValue(request.getReader(), SignIn.class);
            MemberEntity member = memberRepository.findById(signIn.id());

            boolean matches = passwordEncoder.matches(signIn.password(), member.getPassword());
            if (!matches) {
                throw new BadCredentialsException("invalid password");
            }

            MemberSignInUserDetails memberSignInUserDetails = new MemberSignInUserDetails(member.getId(), member.getName(), member.getPassword(), new ArrayList<>());

            usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(memberSignInUserDetails, signIn.password(), memberSignInUserDetails.getAuthorities());
            usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));


        } catch (Exception e) {
            log.error("parsing error : {}", e.getMessage());
        }

        return this.getAuthenticationManager().authenticate(usernamePasswordAuthenticationToken);
    }
}
