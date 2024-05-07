package my.application.gateway.services.member;

import my.application.gateway.entities.mysql.member.MemberEntity;
import my.application.gateway.entities.mysql.member.MemberPrivilege;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

public class MemberSignInUserDetails implements UserDetails {

    private MemberEntity memberEntity;
    private Collection<? extends GrantedAuthority> authorities;
    public MemberSignInUserDetails(MemberEntity memberEntity) {
        this.memberEntity = memberEntity;
        this.authorities = memberEntity.getRoles().stream().map(e -> e.getRole().getMemberRole()).toList();
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
