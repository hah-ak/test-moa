package my.application.user.filter.exception;

import org.springframework.security.core.AuthenticationException;

//근본적인 데이터의 손상등의 케이스
public class AuthenticationExternalDataErrorException extends AuthenticationException {
    public AuthenticationExternalDataErrorException(String msg, Throwable cause) {
        super(msg, cause);
    }
    public AuthenticationExternalDataErrorException(String msg) {
        super(msg);
    }
    public AuthenticationExternalDataErrorException() {
        super("잘못된 데이터가 들어왔거나, 인증관련 항목이 없습니다.");
    }
}
