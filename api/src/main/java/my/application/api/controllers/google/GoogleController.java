package my.application.api.controllers.google;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.*;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.CalendarListEntry;
import com.google.auth.oauth2.OAuth2Credentials;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/google")
public class GoogleController {

    private final GoogleAuthorizationCodeFlow codeFlow;
    private final GoogleClientSecrets secrets;

    private final @Qualifier("desktopAppFlow") GoogleAuthorizationCodeFlow appCodeFlow;
    private final RestTemplate restTemplate;

    //oidc를 위한 흐름, increment credential 위한 흐름.
    @GetMapping("/login")
    public void login(HttpServletResponse response, HttpServletRequest request, @RequestParam("type") String type) throws IOException {
        response.sendRedirect(getRedirectUrl(type));
    }

    private String getRedirectUrl(String type) {
        GoogleAuthorizationCodeRequestUrl googleAuthorizationCodeRequestUrl = codeFlow
                .newAuthorizationUrl()
                .setRedirectUri(secrets.getDetails().getRedirectUris().get(0));

        return switch (type) {
                    case "calendar" -> googleAuthorizationCodeRequestUrl
                            .set("include_granted_scopes", true)
                            .setScopes(CalendarScopes.all())
                            .setState("calendar")
                            .build();
                    default -> googleAuthorizationCodeRequestUrl
                            .setState("oidc")
                            .toString();
                };
    }

    @GetMapping("/accept")
    public void accept(HttpServletRequest httpServletRequest, HttpServletResponse response) throws IOException {

        String state = httpServletRequest.getParameter("state");
        GoogleAuthorizationCodeTokenRequest code = codeFlow.newTokenRequest(httpServletRequest.getParameter("code"));
        code.setRedirectUri(secrets.getDetails().getRedirectUris().get(0));
        code.setGrantType("authorization_code");

        GoogleTokenResponse execute = code.execute();
        if (state.equals("oidc")) {
            GoogleIdToken googleIdToken = execute.parseIdToken();
            Credential andStoreCredential = codeFlow.createAndStoreCredential(execute, googleIdToken.getPayload().getSubject());
            // 클라이언트 측에서 사용할때 필요함.
            // sub와 app전용 아이디와 매핍해서 app전용 아이디를 노출해야함.
            response.setHeader("MY-APP-CREDENTIAL", googleIdToken.getPayload().getSubject());
        }
        else if (state.equals("calendar")) {
//            String header = httpServletRequest.getHeader("MY-APP-CREDENTIAL");
            String header = "107202654188028577798";
            Credential credential = codeFlow.loadCredential(header);
            if (credential == null) {
                response.sendRedirect("/login?type=oidc");
            } else {
                codeFlow.createAndStoreCredential(execute, header);
            }
        }
    }

    @GetMapping("/revoke")
    public void revoke() throws IOException, URISyntaxException {
        //        String header = httpServletRequest.getHeader("MY-APP-CREDENTIAL");
        String header = "107202654188028577798";
        Credential credential = codeFlow.loadCredential(header);
        String accessToken = credential.getAccessToken();

        HttpStatusCode execute = restTemplate.execute(new URI("https://oauth2.googleapis.com/revoke?token=" + accessToken), HttpMethod.POST, null, ClientHttpResponse::getStatusCode);
        log.error("status code : {}", execute.value());
    }

    @GetMapping("/calendar/list")
    public CalendarListEntry calendar(HttpServletRequest httpServletRequest, HttpServletResponse response) throws IOException, GeneralSecurityException {

//        String header = httpServletRequest.getHeader("MY-APP-CREDENTIAL");
        String header = "107202654188028577798";
        Credential credential = codeFlow.loadCredential(header);
        Calendar build = new Calendar.Builder(GoogleNetHttpTransport.newTrustedTransport(), GsonFactory.getDefaultInstance(), credential).build();

        return build.calendarList().get("primary").execute();
    }
}
