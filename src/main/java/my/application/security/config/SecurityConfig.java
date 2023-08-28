package my.application.security.config;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import my.application.security.repositories.JpaRegisteredClientRepository;
import my.application.security.services.oauth.JpaOAuth2AuthorizationConsentService;
import my.application.security.services.oauth.JpaOAuth2AuthorizationService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.annotation.Order;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.jackson2.SecurityJackson2Modules;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.jackson2.OAuth2AuthorizationServerJackson2Module;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@PropertySource("classpath:security-${spring.profiles.active}.yaml")
@ComponentScan(basePackages = {"my.application.security.repositories", "my.application.security.services"})
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

    private final JpaRegisteredClientRepository jpaRegisteredClientRepository;
    private final JpaOAuth2AuthorizationService jpaOAuth2AuthorizationService;
    private final JpaOAuth2AuthorizationConsentService jpaOAuth2AuthorizationConsentService;

    @Bean
    public AuthorizationServerSettings authorizationServerSettings() {
        return AuthorizationServerSettings.builder()
                .authorizationEndpoint("/oauth2/authorize")
                .oidcLogoutEndpoint("/oauth2/logout")
                .oidcUserInfoEndpoint("/oauth2/user-info")
                .build();
    }

    @Bean
    @Order(1)
    public SecurityFilterChain oAuth2SecurityFilterChain(HttpSecurity http) throws Exception {
        OAuth2AuthorizationServerConfigurer oAuth2AuthorizationServerConfigurer = new OAuth2AuthorizationServerConfigurer();
        http.apply(oAuth2AuthorizationServerConfigurer);

        oAuth2AuthorizationServerConfigurer
                .registeredClientRepository(jpaRegisteredClientRepository)
                .authorizationService(jpaOAuth2AuthorizationService)
                .authorizationConsentService(jpaOAuth2AuthorizationConsentService)
                .authorizationServerSettings(authorizationServerSettings());

        return http.build();
    }
    @Bean
    @Order(2)
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/sign-in/**", "{*permit-all}","/oauth2/**").permitAll()
                        .anyRequest().fullyAuthenticated())
//                .oauth2Login(Customizer.withDefaults());
                .oauth2ResourceServer((configurer) ->
                        configurer.jwt(Customizer.withDefaults()));
//                .formLogin(formLogin -> formLogin.usernameParameter("id").passwordParameter("password").loginProcessingUrl("/sign-in/sign-in-process").success);
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
    @Qualifier("jpaRegisteredClientObjectMapper")
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper1 = new ObjectMapper();

        ClassLoader classLoader = JpaRegisteredClientRepository.class.getClassLoader();
        List<Module> securityModules = SecurityJackson2Modules.getModules(classLoader);

        objectMapper1.registerModules(securityModules);
        objectMapper1.registerModule(new OAuth2AuthorizationServerJackson2Module());

        return objectMapper1;
    }
}
