package my.application.security.services.member;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

public class MemberAuthority {

    public static class MemberUserAuthority implements GrantedAuthority {
        private static final String authority = "USER";
        @Getter
        private static final MemberUserAuthority instance = new MemberUserAuthority();
        @Override
        public String getAuthority() {
            return authority;
        }
    }

    public static class MemberAdminAuthority implements GrantedAuthority {
        private static final String authority = "ADMIN";
        @Getter
        private static final MemberAdminAuthority instance = new MemberAdminAuthority();
        @Override
        public String getAuthority() {
            return authority;
        }
    }
}
