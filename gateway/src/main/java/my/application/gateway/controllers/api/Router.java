package my.application.gateway.controllers.api;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("")
@RequiredArgsConstructor
public class Router {


    private final RestTemplate restTemplate;
    @GetMapping("/api")
    public ResponseEntity<Object> getRequest(HttpServletRequest request, HttpServletResponse response) {
        SecurityContextHolder.getContext().getAuthentication();
        return restTemplate.exchange("http://localhost:8083" + request.getPathInfo() + "?" + request.getQueryString(),
                HttpMethod.GET, null, Object.class);
    }

    @PostMapping("/api")
    public ResponseEntity<Object> postRequest(HttpServletRequest request, HttpServletResponse response, @RequestBody Object requestBody) {
        HttpEntity<Object> objectHttpEntity = new HttpEntity<>(requestBody);
        return restTemplate.exchange("http://localhost:8083" + request.getPathInfo() + "?" + request.getQueryString(),
                HttpMethod.POST, objectHttpEntity, Object.class);
    }

    @PutMapping("/api")
    public ResponseEntity<Object> putRequest(HttpServletRequest request, HttpServletResponse response, @RequestBody Object requestBody) {
        HttpEntity<Object> objectHttpEntity = new HttpEntity<>(requestBody);
        return restTemplate.exchange("http://localhost:8083" + request.getPathInfo() + "?" + request.getQueryString(),
                HttpMethod.PUT, objectHttpEntity, Object.class);
    }

    @PatchMapping("/api")
    public ResponseEntity<Object> patchRequest(HttpServletRequest request, HttpServletResponse response, @RequestBody Object requestBody) {
        HttpEntity<Object> objectHttpEntity = new HttpEntity<>(requestBody);
        return restTemplate.exchange("http://localhost:8083" + request.getPathInfo() + "?" + request.getQueryString(),
                HttpMethod.PATCH, objectHttpEntity, Object.class);
    }
}
