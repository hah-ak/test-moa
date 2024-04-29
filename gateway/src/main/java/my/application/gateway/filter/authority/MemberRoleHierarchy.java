package my.application.gateway.filter.authority;

import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashSet;

@Component
public class MemberRoleHierarchy implements RoleHierarchy {

    @Override
    public Collection<? extends GrantedAuthority> getReachableGrantedAuthorities(Collection<? extends GrantedAuthority> authorities) {
        var memberAuthorities = new HashSet<MemberAuthorities>();
        for (GrantedAuthority authority : authorities) {
            addLowerLevelAuthority((MemberAuthorities) authority, memberAuthorities);
        }

        return memberAuthorities;
    }

    private void addLowerLevelAuthority(MemberAuthorities authority, HashSet<MemberAuthorities> hashSet) {
        hashSet.add(authority);
        if (authority == MemberAuthorities.ANONYMOUS) {
            return;
        }

        addLowerLevelAuthority(switch (authority) {
            case ROLE_ADMIN_ALL -> MemberAuthorities.ROLE_ADMIN_0;
            case ROLE_ADMIN_0 -> MemberAuthorities.ROLE_USER;
            default -> MemberAuthorities.ANONYMOUS;
        }, hashSet);
    }
}
