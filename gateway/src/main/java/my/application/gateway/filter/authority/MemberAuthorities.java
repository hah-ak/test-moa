package my.application.gateway.filter.authority;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

@Getter
public enum MemberAuthorities implements GrantedAuthority {

    ROLE_ADMIN_ALL("ROLE_ADMIN_ALL"),
    ROLE_ADMIN_0("ROLE_ADMIN_0"),
    ROLE_USER("ROLE_USER"),
    ANONYMOUS("ANONYMOUS");

    private final String authority;

    MemberAuthorities(String authority) {
        this.authority = authority;
    }
}
