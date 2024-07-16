package web.core.util;

import com.fasterxml.jackson.databind.ObjectMapper;

public class MyAppCommonUtil {
    public static final ObjectMapper defaultObjectMapper = getObjectMapper();

    private static ObjectMapper objectMapper;
    private static ObjectMapper getObjectMapper() {
        if (objectMapper == null) {
            objectMapper = new ObjectMapper();
        }
        return objectMapper;
    }
}
