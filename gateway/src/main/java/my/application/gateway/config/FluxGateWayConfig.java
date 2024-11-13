package my.application.gateway.config;

import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.BooleanSpec;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.http.HttpHeaders;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Collections;
import java.util.UUID;

@Configuration
@RequiredArgsConstructor
public class FluxGateWayConfig {

    @Getter
    @Setter
    public static class RedisConfigurationProperties {
        private Integer database;
        private String password;
        private String host;
        private Integer port;
    }
//
    @Bean
    public KeyResolver userKeyResolver() {
        return exchange -> {
            String glid = exchange.getRequest().getHeaders().getFirst("GLID");

            if (glid == null) {
                glid = "ANONYMOUS:" + UUID.randomUUID().toString();
                String finalGlid = glid;
                exchange.getRequest().mutate().headers(httpHeaders -> httpHeaders.add("GLID", finalGlid));
            }
            exchange.getResponse().getHeaders().put("GLID", Collections.singletonList(glid));
            return Mono.just(glid);
        };
    }

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("ws_route",predicateSpec -> predicateSpec.path("/chat/pub/**","/chat/sub/**")
//                        .filters(gatewayFilterSpec -> gatewayFilterSpec.circuitBreaker(config -> {}))
                        .uri("ws://localhost:9100")
                )
                .route("chat_route",predicateSpec -> predicateSpec.path("/chat/**")
//                        .filters(gatewayFilterSpec -> gatewayFilterSpec.circuitBreaker(config -> {}))
                        .uri("http://localhost:9100")
                )
                .route("user_route",predicateSpec -> predicateSpec.path("/user/**")
                        .filters(gatewayFilterSpec -> gatewayFilterSpec
                                .rewritePath("/(user)/(?<segment>.*)", "/${segment}")
                        )
                        .uri("http://localhost:8110")
                )
                .route("api_route", predicateSpec -> predicateSpec.path("/api/**")
                        .filters(gatewayFilterSpec -> gatewayFilterSpec
                                .requestRateLimiter()
                                .rateLimiter(Bucket4jRateLimiter.class, config -> {
                                    config.setCapacity(1);
                                    config.setRefillTokens(5);
                                    config.setRefillPeriod(Duration.ofMinutes(1));
                                })
                                .and()
                                .rewritePath("/(api|member)/(?<segment>.*)", "/${segment}")
                        )
                        .uri("http://localhost:8100"))
                .build();
    }
}
