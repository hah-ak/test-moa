package my.application.api.controllers.google;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.oauth2.Oauth2Scopes;
import my.application.security.repositories.google.CredentialTokenRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.web.client.RestTemplate;

import java.io.FileReader;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

@Configuration
public class GoogleConfigurations {

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
    @Primary
    public GoogleAuthorizationCodeFlow authorizationCodeFlow(GoogleClientSecrets googleClientSecrets, CredentialTokenRepository credentialTokenRepository) throws GeneralSecurityException, IOException {
        return new GoogleAuthorizationCodeFlow.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                GsonFactory.getDefaultInstance(),
                googleClientSecrets,
                List.of(Oauth2Scopes.OPENID)
        )
                .setDataStoreFactory(new JPADataStoreFactory(credentialTokenRepository))
                .setAccessType("offline")
                .build();
    }

    @Bean("desktopAppFlow")
    public GoogleAuthorizationCodeFlow authorizationCodeFlow2(@Qualifier("deskTopApp") GoogleClientSecrets googleClientSecrets, CredentialTokenRepository credentialTokenRepository) throws GeneralSecurityException, IOException {
        return new GoogleAuthorizationCodeFlow.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                GsonFactory.getDefaultInstance(),
                googleClientSecrets,
                CalendarScopes.all()
        ).setDataStoreFactory(new JPADataStoreFactory(credentialTokenRepository))
                .setAccessType("offline")
                .build();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
