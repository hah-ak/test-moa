package my.application.api.controllers.google;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.*;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.CalendarListEntry;
import com.google.api.services.oauth2.Oauth2Scopes;
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
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/google")
public class GoogleController {

    private final GoogleAuthorizationCodeFlow codeFlow;
    private final GoogleClientSecrets secrets;

    private final @Qualifier("desktopAppFlow") GoogleAuthorizationCodeFlow appCodeFlow;
    private final RestTemplate restTemplate;

    //oidc를 위한 흐름
    @GetMapping("/login")
    public void login(HttpServletResponse response) throws IOException {
        response.sendRedirect(codeFlow
                .newAuthorizationUrl()
                .setRedirectUri(secrets.getDetails().getRedirectUris().get(0))
                .setScopes(List.of(Oauth2Scopes.OPENID))
                .toString()
        );
    }

    @GetMapping("/accept")
    public void accept(HttpServletRequest httpServletRequest, HttpServletResponse response) throws IOException {

        GoogleAuthorizationCodeTokenRequest code = codeFlow.newTokenRequest(httpServletRequest.getParameter("code"));
        code.setRedirectUri(secrets.getDetails().getRedirectUris().get(0));
        code.setGrantType("authorization_code");

        GoogleTokenResponse execute = code.execute();
        GoogleIdToken googleIdToken = execute.parseIdToken();
        GoogleIdToken parse = GoogleIdToken.parse(execute.getFactory(), execute.getIdToken());
        Credential andStoreCredential = codeFlow.createAndStoreCredential(execute, parse.getPayload().getEmail());

        // 클라이언트 측에서 사용할때 필요함.
        response.setHeader("MY-APP-CREDENTIAL",googleIdToken.getPayload().toString());
    }

    @GetMapping("/revoke")
    public void revoke(@RequestParam("id") String id) throws IOException, URISyntaxException {
        Credential credential = codeFlow.loadCredential(id);
        String accessToken = credential.getAccessToken();

        HttpStatusCode execute = restTemplate.execute(new URI("https://oauth2.googleapis.com/reovke?token=" + accessToken), HttpMethod.POST, null, ClientHttpResponse::getStatusCode);
        log.error("status code : {}", execute.value());
    }

    @GetMapping("/complete")
    public String complete() {
        return "complete";
    }

    @GetMapping("/calendar/list")
    public CalendarListEntry calendar(HttpServletRequest httpServletRequest) throws IOException, GeneralSecurityException {
        String header = httpServletRequest.getHeader("MY-APP-CREDENTIAL");
        GoogleIdToken parse = GoogleIdToken.parse(GsonFactory.getDefaultInstance(), header);
        Credential credential = codeFlow.loadCredential(parse.getPayload().getEmail());

        Calendar build = new Calendar.Builder(GoogleNetHttpTransport.newTrustedTransport(), GsonFactory.getDefaultInstance(), credential).build();
        return build.calendarList().get("primary").execute();
    }

    @GetMapping("/calendar/add-scope")
    public void calendarAddScope(HttpServletResponse response) throws IOException {
        response.sendRedirect(
                codeFlow.newAuthorizationUrl()
                        .setScopes(CalendarScopes.all())
                        .setRedirectUri(secrets.getDetails().getRedirectUris().get(0))
                        .set("include_granted_scopes",true)
                        .build()
        );
    }

    @GetMapping("/calendar/accept")
    public void calendarAddScopeAccept(HttpServletRequest httpServletRequest, HttpServletResponse response) throws IOException {

        GoogleAuthorizationCodeTokenRequest code = codeFlow.newTokenRequest(httpServletRequest.getParameter("code"));
        code.setRedirectUri(secrets.getDetails().getRedirectUris().get(0));
        code.setGrantType("authorization_code");

        GoogleTokenResponse execute = code.execute();
        GoogleIdToken googleIdToken = execute.parseIdToken();
        GoogleIdToken parse = GoogleIdToken.parse(execute.getFactory(), execute.getIdToken());
        Credential andStoreCredential = codeFlow.createAndStoreCredential(execute, parse.getPayload().getEmail());

        // 클라이언트 측에서 사용할때 필요함.
        response.setHeader("MY-APP-CREDENTIAL",googleIdToken.getPayload().toString());
    }
}
