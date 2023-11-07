package my.application.security.filter.exception;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

public class MemberAuthenticationEntryPoint implements AuthenticationEntryPoint {
    // 인증예외
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        authException.getCause();
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }
}
