package my.application.user.filter.handlers;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;

@Slf4j
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    // 인증된 사용자가 accessDenied인 경우에만 호출됨
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        log.error("my log : {}", "unauthorized error");
//        response.sendError(HttpStatus.UNAUTHORIZED.value());
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
    }
}
