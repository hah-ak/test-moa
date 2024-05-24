package my.application.gateway.controllers.google;

import com.google.api.client.auth.oauth2.TokenResponseException;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import my.application.gateway.resolvers.MyAppHeaderToken;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;

@RequiredArgsConstructor
@RestControllerAdvice
public class GoogleAuthenticationAdvice {

    private final GoogleController googleController;

    private final String ADMIN_POLICY_ENFORCED = "admin_policy_enforced";
    private final String DISALLOWED_USERAGENT = "disallowed_useragent"; //
    private final String ORG_INTERNAL = "org_internal"; // 프로젝트 자체에서 엑세스를 제어한경우
    private final String INVALID_CLIENT = "invalid_client"; // 비밀번호 에러
    private final String INVALID_GRANT = "invalid_grant"; // 토큰 만료, 무효화 된경우
    private final String REDIRECT_URI_MISMATCH = "redirect_uri_mismatch";
    private final String INVALID_REQUEST = "invalid_request";

    @ExceptionHandler(GoogleJsonResponseException.class)
    public void jsonResponseException(HttpServletResponse response, HttpServletRequest request, GoogleJsonResponseException exception) throws IOException {
        switch (exception.getDetails().getCode()) {
//            case 400 ->
            case 403 -> response.sendRedirect("/google/login?type=" + "calendar");
        }
    }

    @ExceptionHandler(TokenResponseException.class)
    public void tokenResponseException(HttpServletResponse response, HttpServletRequest request, MyAppHeaderToken myAppHeaderToken, TokenResponseException exception) throws IOException {
        switch (exception.getDetails().getError()) {
            case INVALID_GRANT -> googleController.refresh(request, response, myAppHeaderToken);
        }
    }
}
