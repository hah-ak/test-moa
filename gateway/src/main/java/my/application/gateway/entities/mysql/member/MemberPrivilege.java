package my.application.gateway.entities.mysql.member;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

@Getter
public enum MemberPrivilege implements GrantedAuthority {

    FILE_UPLOAD("FILE_UPLOAD"),
    FILE_DOWNLOAD("FILE_DOWNLOAD"),
    VIEW_CONTENT("VIEW_CONTENT"),
    CREATE_CONTENT("CREATE_CONTENT"),
    PERMIT_ALL("PERMIT_ALL");

    private final String authority;

    MemberPrivilege(String authority) {
        this.authority = authority;
    }
}
