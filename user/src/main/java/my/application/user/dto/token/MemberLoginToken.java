package my.application.user.dto.token;

public record MemberLoginToken(
        String email,
        String session,
        String signature,
        String name
) {
}
