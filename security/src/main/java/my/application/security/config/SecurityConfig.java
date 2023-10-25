package my.application.security.config;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.oauth2.Oauth2Scopes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.application.security.services.member.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;
import org.springframework.core.io.Resource;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;
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
    public SecurityFilterChain securityFilterChain(HttpSecurity http, MemberAuthenticationProvider memberAuthenticationProvider) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(SessionManagementConfigurer::disable)
//                .authenticationProvider(memberAuthenticationProvider)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/sign-in/**","/oauth2/**","/google/revoke","/google/login","/.*permit-all.*").permitAll()
                        .requestMatchers("/google/**").access(memberAuthorizationManager)
                        .anyRequest().authenticated()
                )
                .formLogin(formLogin -> formLogin
                        .usernameParameter("id")
                        .passwordParameter("password")
                        .loginProcessingUrl("/sign-in/sign-in-process")
                )
                .oauth2Login(oauth -> oauth
                        .loginProcessingUrl("/google/login")
                        .authorizationEndpoint( endpoint -> endpoint
                                .baseUri("/google/login")
                        )
                )
        ;
//                .oauth2Client(configure -> configure.authorizedClientRepository());
//                .oauth2Login(Customizer.withDefaults());

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
    public MemberAuthenticationProvider memberAuthenticationProvider(MemberSignInUserDetailService userDetailService) {
        return new MemberAuthenticationProvider(userDetailService);
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
