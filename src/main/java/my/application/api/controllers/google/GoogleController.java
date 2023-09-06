package my.application.api.controllers.google;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.auth.oauth2.AccessToken;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

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
    public GoogleIdToken.Payload accept(HttpServletRequest httpServletRequest) throws IOException {
        GoogleAuthorizationCodeTokenRequest code = codeFlow.newTokenRequest(httpServletRequest.getParameter("code"));
        GoogleTokenResponse execute = code.execute();
        GoogleIdToken parse = GoogleIdToken.parse(execute.getFactory(), execute.getIdToken());
        return parse.getPayload();

    }
}
