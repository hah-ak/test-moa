package my.application.security.filter.authority;

import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

public class MemberRoleHierarchy implements RoleHierarchy {

    @Override
    public Collection<? extends GrantedAuthority> getReachableGrantedAuthorities(Collection<? extends GrantedAuthority> authorities) {
        return List.of(new MemberAdminRoleAuthority(), new MemberUserRoleAuthority());
    }
}
