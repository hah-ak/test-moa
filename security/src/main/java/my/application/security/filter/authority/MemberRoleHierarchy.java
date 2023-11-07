package my.application.security.filter.authority;

import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class MemberRoleHierarchy implements RoleHierarchy {



    @Override
    public Collection<? extends GrantedAuthority> getReachableGrantedAuthorities(Collection<? extends GrantedAuthority> authorities) {
        return null;
    }
}
