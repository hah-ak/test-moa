package my.application.security.config;

import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.oauth2.Oauth2Scopes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.application.security.filter.exception.CustomAccessDeniedHandler;
import my.application.security.filter.exception.MemberAuthenticationEntryPoint;
import my.application.security.filter.filter.MemberAuthenticationFilter;
import my.application.security.filter.manager.MemberAuthenticationProviderManager;
import my.application.security.filter.provider.MemberAuthenticationProvider;
import my.application.security.filter.manager.MemberAuthorizationManager;
import my.application.security.filter.filter.MemberNullRepositoryAuthenticationFilter;
import my.application.security.services.member.MemberSignInUserDetailService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;
import org.springframework.core.io.Resource;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.security.web.context.NullSecurityContextRepository;
import org.springframework.security.web.session.SessionManagementFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

@Slf4j
@Configuration
@PropertySource("classpath:security-${spring.profiles.active}.yaml")
@ComponentScan(basePackages = {"my.application.security.services","my.domain.redis","my.domain.mysql"})
@EnableRedisRepositories(basePackages = {"my.application.security.repositories"})
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

    private final MemberAuthorizationManager memberAuthorizationManager;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, MemberAuthenticationProviderManager memberAuthenticationProviderManager) throws Exception {
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
                .addFilterBefore(new MemberNullRepositoryAuthenticationFilter(), AuthorizationFilter.class)
//                .addFilterBefore(new MemberAuthenticationFilter("/**",memberAuthenticationProviderManager), AnonymousAuthenticationFilter.class)
                .exceptionHandling(exception -> exception
                        .accessDeniedHandler(new CustomAccessDeniedHandler())
                        .authenticationEntryPoint(new MemberAuthenticationEntryPoint())
                )
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/sign-in/**","/oauth2/**","/google/revoke","/google/login").permitAll()
                        .requestMatchers(AntPathRequestMatcher.antMatcher("/member/members/permit-all")).permitAll()
                        .requestMatchers("/google/**").access(memberAuthorizationManager)
                        .requestMatchers("/file/**").authenticated()
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
    @Bean
    @Primary
    public GoogleClientSecrets googleClientSecrets(ApplicationContext applicationContext) throws IOException {
        Resource resource = applicationContext.getResource("classpath:/client_secret.json");
        return GoogleClientSecrets.load(GsonFactory.getDefaultInstance(), new FileReader(resource.getFile()));
    }
    @Bean("deskTopApp")
    public GoogleClientSecrets googleClientSecrets2(ApplicationContext applicationContext) throws IOException {
        Resource resource = applicationContext.getResource("classpath:/desktopApp_secret.json");
        return GoogleClientSecrets.load(GsonFactory.getDefaultInstance(), new FileReader(resource.getFile()));
    }
    @Bean
    public ClientRegistrationRepository clientRegistrationRepository(GoogleClientSecrets googleClientSecrets) {
        return new InMemoryClientRegistrationRepository(this.googleClientRegistration(googleClientSecrets));
    }
    private ClientRegistration googleClientRegistration(GoogleClientSecrets googleClientSecrets) {
        return ClientRegistration.withRegistrationId("google")
                .clientId(googleClientSecrets.getWeb().getClientId())
                .clientSecret(googleClientSecrets.getWeb().getClientSecret())
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .redirectUri(googleClientSecrets.getWeb().getRedirectUris().get(0))
                .scope(Oauth2Scopes.OPENID, Oauth2Scopes.USERINFO_EMAIL, Oauth2Scopes.USERINFO_PROFILE)
                .tokenUri(googleClientSecrets.getWeb().getTokenUri())
                .authorizationUri(googleClientSecrets.getWeb().getAuthUri())
                .clientName("Google")
                .build();
    }

}
