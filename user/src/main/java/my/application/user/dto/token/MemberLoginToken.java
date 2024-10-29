package my.application.user.dto.token;

public record MemberLoginToken(
        String id,
        String password,
        String session,
        String encrypt
) {
}
