package my.application.gateway.filter.authority;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

@Getter
public abstract class MemberAuthority implements GrantedAuthority {

}