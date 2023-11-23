package my.application.security.config;

import lombok.RequiredArgsConstructor;
import my.application.security.repositories.google.CredentialTokenRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GoogleAuthorizedClientService implements OAuth2AuthorizedClientService {

    private final CredentialTokenRepository tokenRepository;
    @Override
    public <T extends OAuth2AuthorizedClient> T loadAuthorizedClient(String clientRegistrationId, String principalName) {
        return null;
    }

    @Override
    public void saveAuthorizedClient(OAuth2AuthorizedClient authorizedClient, Authentication principal) {

    }

    @Override
    public void removeAuthorizedClient(String clientRegistrationId, String principalName) {

    }
}
