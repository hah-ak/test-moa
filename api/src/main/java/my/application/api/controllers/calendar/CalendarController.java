package my.application.api.controllers.calendar;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.CalendarListEntry;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class CalendarController {

//    @GetMapping("/calendar/list")
//    public CalendarListEntry calendar(HttpServletRequest httpServletRequest, HttpServletResponse response) throws IOException, GeneralSecurityException {
//
//        codeFlow
//                .newAuthorizationUrl()
//                .setRedirectUri(secrets.getDetails().getRedirectUris().get(0))
//                .set("include_granted_scopes", true)
//                .setScopes(CalendarScopes.all())
//                .setState("calendar")
//                .build();
//
//        Credential credential = codeFlow.loadCredential(myAppHeaderToken.getId());
//        Calendar build = new Calendar.Builder(GoogleNetHttpTransport.newTrustedTransport(), GsonFactory.getDefaultInstance(), credential).build();
//
//        return build.calendarList().get("primary").execute();
//    }
}
