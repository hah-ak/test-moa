package my.application.member.filter.exception;

import org.springframework.security.core.AuthenticationException;

//근본적인 데이터의 손상등의 케이스
public class AuthenticationExternalDataErrorException extends AuthenticationException {
    public AuthenticationExternalDataErrorException(String msg, Throwable cause) {
        super(msg, cause);
    }
    public AuthenticationExternalDataErrorException(String msg) {
        super(msg);
    }
}
