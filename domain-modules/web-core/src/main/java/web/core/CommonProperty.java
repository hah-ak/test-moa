package web.core;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@Getter
public class CommonProperty {
    @Value("${user.server.domain}")
    private String USER_SERVER_DOMAIN;
}
