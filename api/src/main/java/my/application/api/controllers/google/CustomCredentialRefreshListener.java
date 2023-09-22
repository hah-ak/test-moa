package my.application.api.controllers.google;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.CredentialRefreshListener;
import com.google.api.client.auth.oauth2.TokenErrorResponse;
import com.google.api.client.auth.oauth2.TokenResponse;

import java.io.IOException;

public class CustomCredentialRefreshListener implements CredentialRefreshListener {
    @Override
    public void onTokenResponse(Credential credential, TokenResponse tokenResponse) throws IOException {

    }

    @Override
    public void onTokenErrorResponse(Credential credential, TokenErrorResponse tokenErrorResponse) throws IOException {

    }
}
