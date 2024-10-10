package my.application.api.controllers.calendar;

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
