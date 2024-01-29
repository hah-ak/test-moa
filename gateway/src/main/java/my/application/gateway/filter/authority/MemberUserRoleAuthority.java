package my.application.gateway.filter.authority;

public class MemberUserRoleAuthority extends MemberAuthority {
    public static final String authority = "ROLE_USER";
    @Override
    public String getAuthority() {
        return authority;
    }
}