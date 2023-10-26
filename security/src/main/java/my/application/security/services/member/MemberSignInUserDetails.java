package my.application.security.services.member;

import my.domain.mysql.entities.MemberEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class MemberSignInUserDetails implements UserDetails {

    private MemberEntity memberEntity;
    private Collection<? extends GrantedAuthority> authorities;

    public MemberSignInUserDetails(MemberEntity memberEntity) {
        this.memberEntity = memberEntity;
        setAuthorities();
    }

    private void setAuthorities() {
        authorities = List.of(MemberAuthority.MemberUserAuthority.getInstance());
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
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
