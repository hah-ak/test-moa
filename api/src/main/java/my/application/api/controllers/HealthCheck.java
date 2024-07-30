package my.application.api.controllers;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class HealthCheck {

    @AllArgsConstructor
    @ToString
    @Getter
    private static class HealthCheckResponse {
        private String status;
        private String serverName;
    }

    @GetMapping(value = "/health/check")
    public Object check() {
        return new HealthCheckResponse("OK","API");
    }
}
