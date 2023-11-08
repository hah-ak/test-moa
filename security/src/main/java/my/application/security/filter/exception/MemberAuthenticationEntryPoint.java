package my.application.security.filter.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import org.apache.http.entity.ContentType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

public class MemberAuthenticationEntryPoint implements AuthenticationEntryPoint {
    // 인증되지 않으면 authenticationEntrypoint구성
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Getter
    private static class ErrorMessageObject {
        private String msg;
        private String url;

        private ErrorMessageObject(String msg, String url) {
            this.msg = msg;
            this.url = url;
        }
    }
    // 인증예외
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        authException.getCause();
//        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType(ContentType.APPLICATION_JSON.getMimeType());
        response.getWriter().write(objectMapper.writeValueAsString(new ErrorMessageObject("messsage 도착", "http://url")));
    }
}
