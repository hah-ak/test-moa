package my.application.security.dto.token;

public record MemberLoginToken(
        String id,
        String password,
        String session
) {
}
