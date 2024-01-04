package my.application.gateway.dto.token;

public record MemberLoginToken(
        String id,
        String password,
        String session,
        String encrypt
) {
}
