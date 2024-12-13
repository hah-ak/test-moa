package web.core.common;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:common-${spring.profiles.active}.properties")
@Getter
public class CommonProperty {
    @Value("${user.server.domain}")
    private String USER_SERVER_DOMAIN;
}
