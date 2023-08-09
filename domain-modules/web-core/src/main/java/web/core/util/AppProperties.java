package web.core.util;

import org.springframework.beans.factory.annotation.Value;

public class AppProperties {

    public static String ACTIVE_PROFILE;

    @Value("${spring.profiles.active}")
    public void setActiveProfile(String value) {
        ACTIVE_PROFILE = value;
    }
}
