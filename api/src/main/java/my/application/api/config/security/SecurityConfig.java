package my.application.api.config.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Slf4j
@Configuration
public class SecurityConfig {

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOriginPatterns(List.of("localhost"));
        corsConfiguration.setAllowedMethods(List.of(CorsConfiguration.ALL));
        corsConfiguration.setAllowedHeaders(List.of(CorsConfiguration.ALL));
        corsConfiguration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**",corsConfiguration);
        return urlBasedCorsConfigurationSource;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

//        httpSecurity.authorizeHttpRequests(auth -> auth.requestMatchers("/**").permitAll())
//                        .requestMatchers("/admin/**").hasRole("ADMIN")
//                        .requestMatchers("/sign-in/**", "/oauth2/**", "/google/revoke", "/google/login").permitAll()
//                        .requestMatchers(AntPathRequestMatcher.antMatcher("/member/members/permit-all")).permitAll())
//                .exceptionHandling(exception -> exception
//                        .accessDeniedHandler(null)
//                );

        return httpSecurity.build();
    }
}
