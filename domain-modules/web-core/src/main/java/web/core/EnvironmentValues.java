package web.core;

public class EnvironmentValues {
    public static final String ACTIVE_PROFILE = System.getProperty("spring.profiles.active");
    public static final String WITHOUT_KAFKA = System.getProperty("without.kafka");
}
