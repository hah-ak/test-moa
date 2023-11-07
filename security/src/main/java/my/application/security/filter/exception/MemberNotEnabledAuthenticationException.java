package my.application.security.filter.exception;

import org.springframework.security.core.AuthenticationException;

public class MemberNotEnabledAuthenticationException extends AuthenticationException {


    public MemberNotEnabledAuthenticationException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public MemberNotEnabledAuthenticationException(String msg) {
        super(msg);
    }
}
