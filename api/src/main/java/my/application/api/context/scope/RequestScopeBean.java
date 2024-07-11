package my.application.api.context.scope;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import java.util.Optional;

@Setter
@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
@RequiredArgsConstructor
public class RequestScopeBean {

    private String memberData;
    private String otherMemberData;

    public Optional<String> getMemberData() {
        return Optional.ofNullable(this.memberData);
    }

    public Optional<String> otherMemberData() {
        return Optional.ofNullable(this.otherMemberData);
    }
}
