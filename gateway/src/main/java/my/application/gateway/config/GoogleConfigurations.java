package my.application.gateway.config;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.oauth2.Oauth2Scopes;
import my.application.gateway.controllers.google.JPADataStoreFactory;
import my.application.gateway.repositories.google.CredentialTokenRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

@Configuration
public class GoogleConfigurations {
    @Bean
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
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
