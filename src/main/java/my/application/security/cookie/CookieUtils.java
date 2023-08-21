package my.application.security.cookie;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import web.core.util.EnvironmentValues;

public class CookieUtils {
    public static void createLoginCookie(HttpServletRequest request, HttpServletResponse response) {

        Cookie cookie = new Cookie("MSESSION", request.getSession(false).getId());
        cookie.setHttpOnly(true);
        cookie.setMaxAge(request.getSession(false).getMaxInactiveInterval());
        cookie.setDomain(EnvironmentValues.ACTIVE_PROFILE.equals("dev") ? "localhost" : "localhost2");

        response.addCookie(cookie);
    }

    public static void createLoginHeader(HttpServletRequest request, HttpServletResponse response) {
        response.setHeader("bearer","");
    }
}
