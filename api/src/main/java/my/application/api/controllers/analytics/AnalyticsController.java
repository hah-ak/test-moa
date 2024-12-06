package my.application.api.controllers.analytics;

import lombok.RequiredArgsConstructor;
import my.application.api.services.analytics.AnalyticsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/analytics")
@RequiredArgsConstructor
public class AnalyticsController {
    private final AnalyticsService analyticsService;
    @GetMapping("/report")
    public String analyticsReport() {

        return analyticsService.getRunReport();
    }

}
