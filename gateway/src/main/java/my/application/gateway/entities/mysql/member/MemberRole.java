package my.application.gateway.entities.mysql.member;

import lombok.Getter;

import java.util.List;

@Getter
public enum MemberRole {
    ROLE_ADMIN_ALL(List.of(MemberAuthority.values())),
    ROLE_ADMIN_0(List.of(MemberAuthority.values())),
    ROLE_USER(List.of(MemberAuthority.values())),
    ANONYMOUS(List.of(MemberAuthority.PERMIT_ALL));

    private final List<MemberAuthority> authorities;

    MemberRole(List<MemberAuthority> authorities) {
        this.authorities = authorities;
    }
}
