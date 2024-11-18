package my.application.user.services.member;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collection;

public class MemberSignInUserDetails implements UserDetails {
    private final String email;
    private final String nickName;
    private final String password;
    private final Collection<? extends GrantedAuthority> authorities;
    public MemberSignInUserDetails(String email, String nickName, String password, Collection<? extends GrantedAuthority> authorities) {
        this.email = email;
        this.nickName = nickName;
        this.password = password;
        this.authorities = authorities;
    }
    public String getUserNickName() {
        return this.nickName;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }
    @Override
    public String getUsername() {
        return this.email;
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
        return LocalDateTime.now(ZoneId.of("UTC")).minusMonths(3).isAfter(LocalDateTime.now(ZoneId.of("UTC")));
    }

    @Override
    public boolean isEnabled() {
        return isAccountNonExpired() && isAccountNonLocked() && isCredentialsNonExpired();
    }
}
