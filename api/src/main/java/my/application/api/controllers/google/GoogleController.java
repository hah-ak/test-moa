package my.application.api.controllers.google;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.json.gson.GsonFactory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.GeneralSecurityException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/google")
public class GoogleController {

    private final GoogleAuthorizationCodeFlow codeFlow;
    @GetMapping("/login")
    public String login() {
        return codeFlow.newAuthorizationUrl().toString();
    }

    @GetMapping("/accept")
    public void accept(HttpServletRequest httpServletRequest, HttpServletResponse response) throws IOException {
        GoogleAuthorizationCodeTokenRequest code = codeFlow.newTokenRequest(httpServletRequest.getParameter("code"));
        GoogleTokenResponse execute = code.execute();
        GoogleIdToken parse = GoogleIdToken.parse(execute.getFactory(), execute.getIdToken());
        Credential andStoreCredential = codeFlow.createAndStoreCredential(execute, parse.getPayload().getSubject());
        response.setHeader("JWT-TOKEN",parse.getPayload().toString());
    }

    @GetMapping("/calendar")
    public void calendar(HttpServletRequest httpServletRequest) throws IOException, GeneralSecurityException {
        String header = httpServletRequest.getHeader("JWT-TOKEN");
        GoogleIdToken parse = GoogleIdToken.parse(GsonFactory.getDefaultInstance(), header);
//        new HttpCredentialsAdapter(codeFlow.loadCredential(parse.toString());
//        new Calendar.Builder(GoogleNetHttpTransport.newTrustedTransport(), GsonFactory.getDefaultInstance(),)
    }
}
