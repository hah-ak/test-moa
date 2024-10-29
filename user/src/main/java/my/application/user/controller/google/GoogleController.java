package my.application.user.controller.google;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.TokenResponseException;
import com.google.api.client.googleapis.auth.oauth2.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.application.user.dto.signUp.SignUp;
import my.application.user.resolvers.MyAppHeaderToken;
import my.application.user.services.member.MemberSecurityService;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/google")
public class GoogleController {

    private final GoogleAuthorizationCodeFlow codeFlow;
    private final GoogleClientSecrets secrets;
    private final MemberSecurityService memberSecurityService;
    private final RestTemplate restTemplate;

    //oidc를 위한 흐름, increment credential 위한 흐름.
    @GetMapping("/login")
    public void login(HttpServletResponse response, HttpServletRequest request) throws IOException {
        response.sendRedirect(
                codeFlow
                .newAuthorizationUrl()
                .setRedirectUri(secrets.getDetails().getRedirectUris().get(0))
                .setState("oidc")
                .toString()
        );
    }

    public void refresh(HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse, MyAppHeaderToken myAppHeaderToken) throws IOException {
        try {
            Credential credential = codeFlow.loadCredential(myAppHeaderToken.getId());
            if (credential.refreshToken()) {
                httpServletResponse.sendRedirect(httpServletRequest.getRequestURI());
            } else {

            }
        } catch (TokenResponseException exception) {
            httpServletResponse.sendRedirect("/google/login?type=oidc");
        }
    }

    @GetMapping("/accept")
    public void accept(HttpServletRequest httpServletRequest, HttpServletResponse response, MyAppHeaderToken myAppHeaderToken) throws IOException {

        String state = httpServletRequest.getParameter("state");
        GoogleAuthorizationCodeTokenRequest code = codeFlow.newTokenRequest(httpServletRequest.getParameter("code"));
        code.setRedirectUri(secrets.getDetails().getRedirectUris().get(0));
        code.setGrantType("authorization_code");

        GoogleTokenResponse execute = code.execute();
        if (state.equals("oidc")) {
            GoogleIdToken googleIdToken = execute.parseIdToken();

            memberSecurityService.signUpProcess(
                    new SignUp(
                            googleIdToken.getPayload().getEmail(),
                            UUID.randomUUID().toString(),
                            null,
                            googleIdToken.getPayload().get("name").toString()
                    )
            );

            Credential andStoreCredential = codeFlow.createAndStoreCredential(execute, googleIdToken.getPayload().getSubject());
            // 클라이언트 측에서 사용할때 필요함.
            // sub와 app전용 아이디와 매핍해서 app전용 아이디를 노출해야함.

            response.setHeader("MY-APP-CREDENTIAL", googleIdToken.getPayload().getSubject());
        }
        else if (state.equals("calendar")) {
            Credential credential = codeFlow.loadCredential(myAppHeaderToken.getId());
            if (credential == null) {
                response.sendRedirect("/google/login?type=oidc");
            } else {
                codeFlow.createAndStoreCredential(execute, myAppHeaderToken.getId());
            }
        }
    }

    @GetMapping("/revoke")
    public void revoke(HttpServletRequest httpServletRequest,MyAppHeaderToken myAppHeaderToken) throws IOException, URISyntaxException {
        Credential credential = codeFlow.loadCredential(myAppHeaderToken.getId());
        String accessToken = credential.getAccessToken();

        HttpStatusCode execute = restTemplate.execute(new URI("https://oauth2.googleapis.com/revoke?token=" + accessToken), HttpMethod.POST, null, ClientHttpResponse::getStatusCode);
    }
}
