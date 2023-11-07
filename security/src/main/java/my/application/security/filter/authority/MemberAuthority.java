package my.application.security.filter.authority;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

public class MemberAuthority {

    public static class MemberUserRoleAuthority implements GrantedAuthority {
        private static final String authority = "ROLE_USER";
        @Getter
        private static final MemberUserRoleAuthority instance = new MemberUserRoleAuthority();
        @Override
        public String getAuthority() {
            return authority;
        }
    }

    public static class MemberAdminRoleAuthority implements GrantedAuthority {
        private static final String authority = "ROLE_ADMIN";
        @Getter
        private static final MemberAdminRoleAuthority instance = new MemberAdminRoleAuthority();
        @Override
        public String getAuthority() {
            return authority;
        }
    }

    public static class MemberFileAuthority implements GrantedAuthority {

        private static final String authority = "FILE";
        @Getter
        private static final MemberFileAuthority instance = new MemberFileAuthority();
        @Override
        public String getAuthority() {
            return authority;
        }
    }
}
