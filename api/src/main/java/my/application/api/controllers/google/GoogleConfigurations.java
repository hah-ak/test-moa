package my.application.api.controllers.google;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.calendar.CalendarScopes;
import my.domain.redis.repositories.google.CredentialTokenRepository;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.io.FileReader;
import java.io.IOException;
import java.security.GeneralSecurityException;

@Configuration
public class GoogleConfigurations {

    @Bean
    public GoogleClientSecrets googleClientSecrets(ApplicationContext applicationContext) throws IOException {
        Resource resource = applicationContext.getResource("classpath:/client_secret.json");
        return GoogleClientSecrets.load(GsonFactory.getDefaultInstance(), new FileReader(resource.getFile()));
    }

    @Bean
    public GoogleAuthorizationCodeFlow authorizationCodeFlow(GoogleClientSecrets googleClientSecrets, CredentialTokenRepository credentialTokenRepository) throws GeneralSecurityException, IOException {
        return new GoogleAuthorizationCodeFlow.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                GsonFactory.getDefaultInstance(),
                googleClientSecrets,
                CalendarScopes.all()
        ).setDataStoreFactory(new JPADataStoreFactory(credentialTokenRepository)).build();
    }
}
