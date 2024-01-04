package my.application.gateway.controllers.api;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ApiController {

    private final RestTemplate restTemplate;
    @GetMapping
    public Object apiRequest(HttpServletRequest request, HttpServletResponse response) {
        return restTemplate.exchange("http://localhost:8083" + request.getPathInfo() + "?" + request.getQueryString(),
                HttpMethod.GET, null, Object.class);
    }

    @PostMapping
    public Object apiRequest(HttpServletRequest request, HttpServletResponse response, @RequestBody Object requestBody) {
        HttpEntity<Object> objectHttpEntity = new HttpEntity<>(requestBody);
        return restTemplate.exchange("http://localhost:8083" + request.getPathInfo() + "?" + request.getQueryString(),
                HttpMethod.POST, objectHttpEntity, Object.class);
    }
}
