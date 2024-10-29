package my.application.user.config;

import lombok.extern.slf4j.Slf4j;
import my.application.user.filter.handlers.CustomAccessDeniedHandler;
import my.application.user.filter.handlers.MemberAuthenticationEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.NullSecurityContextRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Slf4j
@Configuration
public class DefaultHttpSecurity {
    // 추후에 정삭적은 커스텀필터를 하나 만들어서 디비와 연동해서 사용해야함.
    public void setDefaultSecurity(HttpSecurity httpSecurity) throws Exception {
        // .addFilterAt(new MemberAuthenticationFilter("/**", new MemberAuthenticationProviderManager(memberAuthenticationProvider)), SecurityContextHolderFilter.class) 이렇게 하면 전지역에서 authenticate를 시도하는 형태가되어 전부다 authenticate성공으로 보고 리다이렉트를 돌게되어 무한루프발행.
        // 리퀘스트가 생성되고 각 필터에서 그 상황에 맞는 필터링과정을 걸치고 이 과정에서 필요하다면 authentication 객체를 생성하고 그걸 활용해 authenticated인지 판단후 authorization을 authorization필터로 확인하는 과정임.
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .securityContext(securityContext -> securityContext
                        .securityContextRepository(new NullSecurityContextRepository())
                )
                .exceptionHandling(exception -> exception
                        .accessDeniedHandler(new CustomAccessDeniedHandler())
                        .authenticationEntryPoint(new MemberAuthenticationEntryPoint())
                );
    }

    public void setDefaultAuthorizationRequest(HttpSecurity httpSecurity) throws Exception {
        // 역할은 권한의 덩어리, 그러므로 권한을 찾을때 역할도 그 안에 있으면 찾을 수 있도록 시큐리티는 설계되어있음.(hasRole도 결국 hasAuthority로 찾도록 내부적설계됨)
        httpSecurity.authorizeHttpRequests(auth -> auth
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .requestMatchers("/sign-in/**", "/oauth2/**", "/google/revoke", "/google/login").permitAll()
                .requestMatchers(AntPathRequestMatcher.antMatcher("/member/members/permit-all")).permitAll()
        );
    }

    // Bcrypt는 매번 내부적으로 임의의 salt를 이용해 digest 로 만듦, 그렇기 때문에 인코딩 할 때 마다 매번 다른 값이 나옴. 일치여부는 객체내장 함수 사용
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
