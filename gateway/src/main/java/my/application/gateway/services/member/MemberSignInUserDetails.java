package my.application.gateway.services.member;

import my.application.gateway.entities.mysql.member.MemberEntity;
import my.application.gateway.filter.authority.MemberAdminRoleAuthority;
import my.application.gateway.entities.mysql.member.MemberAuthority;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MemberSignInUserDetails implements UserDetails {

    private MemberEntity memberEntity;
    private List<MemberAuthority> authorities = new ArrayList<>();
    public MemberSignInUserDetails(MemberEntity memberEntity) {
        this.memberEntity = memberEntity;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return memberEntity.getPassword();
    }

    @Override
    public String getUsername() {
        return memberEntity.getId();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !this.memberEntity.isSuspended();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return LocalDateTime.now(ZoneId.of("UTC")).minusMonths(3).isAfter(this.memberEntity.getPasswordUpdateDateTime());
    }

    @Override
    public boolean isEnabled() {
        return isAccountNonExpired() && isAccountNonLocked() && isCredentialsNonExpired();
    }
}
