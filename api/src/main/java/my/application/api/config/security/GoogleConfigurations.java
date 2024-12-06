package my.application.api.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class GoogleConfigurations {
//    @Bean
//    public GoogleAuthorizationCodeFlow authorizationCodeFlow(GoogleClientSecrets googleClientSecrets, CredentialTokenRepository credentialTokenRepository) throws GeneralSecurityException, IOException {
//        return new GoogleAuthorizationCodeFlow.Builder(
//                GoogleNetHttpTransport.newTrustedTransport(),
//                GsonFactory.getDefaultInstance(),
//                googleClientSecrets,
//                List.of(Oauth2Scopes.OPENID)
//        )
//                .setDataStoreFactory(new JPADataStoreFactory(credentialTokenRepository))
//                .setAccessType("offline")
//                .build();
//    }

}
