package my.application.api.dto.member;

public record MemberDTO(
        Integer memNo,
        String id,
        String password,
        String name
){
}
