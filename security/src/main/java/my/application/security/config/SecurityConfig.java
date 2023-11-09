package my.application.security.config;

import lombok.extern.slf4j.Slf4j;
import my.application.security.filter.filter.MemberAuthenticationProcessingFilter;
import my.application.security.filter.filter.MemberNullRepositoryAuthenticationFilter;
import my.application.security.filter.handlers.CustomAccessDeniedHandler;
import my.application.security.filter.handlers.MemberAuthenticationEntryPoint;
import my.application.security.filter.manager.MemberAuthenticationProcessingProviderManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;
import org.springframework.security.web.context.NullSecurityContextRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Slf4j
@Configuration
@PropertySource("classpath:security-${spring.profiles.active}.yaml")
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, MemberAuthenticationProcessingProviderManager memberAuthenticationProcessingProviderManager) throws Exception {
        // .addFilterAt(new MemberAuthenticationFilter("/**", new MemberAuthenticationProviderManager(memberAuthenticationProvider)), SecurityContextHolderFilter.class) 이렇게 하면 전지역에서 authenticate를 시도하는 형태가되어 전부다 authenticate성공으로 보고 리다이렉트를 돌게되어 무한루프발행.
        // 리퀘스트가 생성되고 각 필터에서 그 상황에 맞는 필터링과정을 걸치고 이 과정에서 필요하다면 authentication 객체를 생성하고 그걸 활용해 authenticated인지 판단후 authorization을 authorization필터로 확인하는 과정임.
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .securityContext(securityContext->securityContext
                        .securityContextRepository(new NullSecurityContextRepository())
                )
                // 일반적으로 authenticate를 동작하는 필터
                .addFilterBefore(new MemberNullRepositoryAuthenticationFilter(memberAuthenticationProcessingProviderManager), AuthorizationFilter.class)
                // entry point를 통해 들어온 경우에 만 동작하는 필터가 된다. 즉 authenticate 실패한경우에만 동작.
                .addFilterBefore(new MemberAuthenticationProcessingFilter(memberAuthenticationProcessingProviderManager), MemberNullRepositoryAuthenticationFilter.class)
                .exceptionHandling(exception -> exception
                        .accessDeniedHandler(new CustomAccessDeniedHandler())
                        .authenticationEntryPoint(new MemberAuthenticationEntryPoint())
                )
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/sign-in/**","/oauth2/**","/google/revoke","/google/login").permitAll()
                        .requestMatchers(AntPathRequestMatcher.antMatcher("/member/members/permit-all")).permitAll()
                        .anyRequest().authenticated()
                );
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOriginPatterns(List.of("http://localhost:3003"));
        corsConfiguration.setAllowedMethods(List.of(CorsConfiguration.ALL));
        corsConfiguration.setAllowedHeaders(List.of(CorsConfiguration.ALL));
        corsConfiguration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**",corsConfiguration);
        return urlBasedCorsConfigurationSource;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
