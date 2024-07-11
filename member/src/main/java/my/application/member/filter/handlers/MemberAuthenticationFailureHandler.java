package my.application.member.filter.handlers;

import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

public class MemberAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    public MemberAuthenticationFailureHandler() {
        super(MemberAuthenticationEntryPoint.SIGN_IN_URL);
    }
}
