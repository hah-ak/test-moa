package my.application.security.filter.authority;

public class MemberFileAuthority extends MemberAuthority{
    public static final String authority = "FILE";
    @Override
    public String getAuthority() {
        return authority;
    }
}