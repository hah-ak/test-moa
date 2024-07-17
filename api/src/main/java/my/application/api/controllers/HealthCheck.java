package my.application.api.controllers;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health")
public class HealthCheck {

    @AllArgsConstructor
    @ToString
    @Getter
    private static class HealthCheckResponse {
        private String status;
        private String serverName;
    }

    @GetMapping(value = "/check")
    public Object check() {
        return new HealthCheckResponse("OK","API");
    }
}
