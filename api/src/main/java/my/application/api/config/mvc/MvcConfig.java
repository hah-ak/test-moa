package my.application.api.config.mvc;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.application.api.resolvers.UserTokenHeaderMethodArgumentResolver;
import my.application.api.services.file.FileUtils;
import my.application.security.common.DefaultHttpSecurity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Slf4j
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class MvcConfig implements WebMvcConfigurer {
    private final DefaultHttpSecurity defaultHttpSecurity;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        defaultHttpSecurity.setDefaultSecurity(httpSecurity);
        defaultHttpSecurity.setDefaultAuthorizationRequest(httpSecurity);
        httpSecurity.authorizeHttpRequests(auth -> auth.anyRequest().authenticated());
        return httpSecurity.build();
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new UserTokenHeaderMethodArgumentResolver());
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedMethods("*").allowedHeaders("*").allowedOriginPatterns("http://localhost:3003");
    }
}
