package my.application.member.entities.mysql.member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

@Getter
@AllArgsConstructor
public enum MemberRole implements GrantedAuthority {
    ROLE_ADMIN("ADMIN"),
    ROLE_USER("USER"),
    ANONYMOUS("ANONYMOUS");

    private final String authority;

    @Override
    public String getAuthority() {
        return this.authority;
    }


}
