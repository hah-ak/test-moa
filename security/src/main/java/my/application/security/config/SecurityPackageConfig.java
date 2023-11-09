package my.application.security.config;

import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.oauth2.Oauth2Scopes;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;

import java.io.FileReader;
import java.io.IOException;

@Configuration
@ComponentScan(basePackages = {"my.application.security.services","my.domain.redis","my.domain.mysql"})
@EnableRedisRepositories(basePackages = {"my.application.security.repositories"})
public class SecurityPackageConfig {
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
