package web.core.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class SecretKeyUtil {

    public final static class JWESecretProperties {

        private final String password;
        private final String path;

        private JWESecretProperties(String password, String path) {
            this.password = password;
            this.path = path;
        }

        public String getPassword() {
            return password;
        }

        public String getPath() {
            return path;
        }
    }

    public static final JWESecretProperties jWESecretProperties;

    static {
        try (InputStream resourceAsStream = SecretKeyUtil.class.getResourceAsStream("/secret-security-" + EnvironmentValues.ACTIVE_PROFILE + ".properties");) {
            Properties properties = new Properties();
            properties.load(resourceAsStream);
            jWESecretProperties = new JWESecretProperties(
                    properties.getProperty("JWE.password"),
                    properties.getProperty("JWE.cert.file.path"));
        } catch (IOException ignore) {
            System.out.println("error");
            throw new RuntimeException(ignore);
        }
    }

}
