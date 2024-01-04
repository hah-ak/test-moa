package my.application.gateway.filter.authority;

public class MemberAdminRoleAuthority extends MemberAuthority{
    public static final String authority = "ROLE_ADMIN";
    @Override
    public String getAuthority() {
        return authority;
    }
}
