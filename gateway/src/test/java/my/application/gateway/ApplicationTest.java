package my.application.gateway;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.MatcherAssert;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.test.web.servlet.result.JsonPathResultMatchers;
import org.springframework.web.util.UriTemplateHandler;

import java.util.Map;

@SpringBootTest(properties = {"spring.profiles.active=local"}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApplicationTest {

    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate testRestTemplate;

    @BeforeEach
    public void setUp() {
        testRestTemplate.getRestTemplate().setRequestFactory(new SimpleClientHttpRequestFactory());
    }

    @Test
    public void get() {
        Assertions.assertThat(testRestTemplate.getForEntity("/api/health/check", JSONObject.class).getStatusCode())
                .isEqualTo(HttpStatus.OK);
    }

}
