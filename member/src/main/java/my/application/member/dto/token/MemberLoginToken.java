package my.application.member.dto.token;

public record MemberLoginToken(
        String id,
        String password,
        String session,
        String encrypt
) {
}
