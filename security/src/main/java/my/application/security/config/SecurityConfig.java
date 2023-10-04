package my.application.security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.AuthenticatedPrincipalOAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@PropertySource("classpath:security-${spring.profiles.active}.yaml")
@ComponentScan(basePackages = {"my.application.security.services","my.domain.redis","my.domain.mysql"})
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(SessionManagementConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/sign-in/**", "{*permit-all}","/oauth2/**","/google/revoke","google/login").permitAll()
                        .requestMatchers("/google/**").authenticated()
                        .anyRequest().fullyAuthenticated())
                .exceptionHandling(configurer -> configurer.accessDeniedHandler(new CustomAccessDeniedHandler()))
                .oauth2Login(configure -> configure.loginPage("/google/login"))
                .oauth2Client(configure -> configure.authorizedClientRepository());
//                .oauth2Login(Customizer.withDefaults());
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
    public OAuth2AuthorizedClientService googleAuthorizedClientService() {
        return new OAuth2
    }

    @Bean
    public OAuth2AuthorizedClientRepository googleAuthorizedClientRepository() {
        new AuthenticatedPrincipalOAuth2AuthorizedClientRepository()
    }

}
