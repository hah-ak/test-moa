package my.application.gateway.config;

import com.google.common.collect.Lists;
import com.google.common.net.HttpHeaders;
import com.nimbusds.jwt.SignedJWT;
import io.github.bucket4j.distributed.ExpirationAfterWriteStrategy;
import io.github.bucket4j.distributed.proxy.AsyncProxyManager;
import io.github.bucket4j.redis.lettuce.Bucket4jLettuce;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.codec.ByteArrayCodec;
import io.lettuce.core.codec.RedisCodec;
import io.lettuce.core.codec.StringCodec;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import my.application.gateway.utils.MyAppJwtUtils;
import my.domain.redis.RedisCommonTemplate;
import my.domain.redis.RedisSessionTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.cloud.gateway.server.mvc.filter.*;
import org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions;
import org.springframework.cloud.gateway.server.mvc.predicate.GatewayRequestPredicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.function.HandlerFilterFunction;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerRequest;
import org.springframework.web.servlet.function.ServerResponse;

import javax.naming.AuthenticationException;
import java.net.InetSocketAddress;
import java.time.Duration;
import java.util.List;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class MvcConfig implements WebMvcConfigurer {



    private final RedisSessionTemplate<String, List<Map<String, String>>> redisTemplate;

    @Bean
    public Customizer<Resilience4JCircuitBreakerFactory> circuitBreakerFactoryCustomizer() {
        CircuitBreakerConfig circuitBreakerConfig = CircuitBreakerConfig.custom()
                .failureRateThreshold(50)
                .waitDurationInOpenState(Duration.ofMillis(1000))
                .slidingWindowSize(2)
                .build();
        TimeLimiterConfig timeLimiterConfig = TimeLimiterConfig.custom()
                .timeoutDuration(Duration.ofSeconds(4))
                .build();

        return factory -> factory.configureDefault(id -> new Resilience4JConfigBuilder(id)
                .timeLimiterConfig(timeLimiterConfig)
                .circuitBreakerConfig(circuitBreakerConfig)
                .build());
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedMethods("*")
                .allowedOrigins("http://localhost:[*]");
    }

    @Bean
    public RouterFunction<ServerResponse> gatewayRouter() {
//        GatewayRequestPredicates.after(ZonedDateTime.parse("2017-01-20T17:42:47.789-07:00[America/Denver]"))
        return GatewayRouterFunctions
                .route("common_route")
                .route(
                        GatewayRequestPredicates.path("/chat/**"),
                        HandlerFunctions.http("http://localhost:9100"))
                .route(
                        GatewayRequestPredicates.path("/api/**"),
                        HandlerFunctions.http("http://localhost:8100"))
                .route(
                        GatewayRequestPredicates.path("/member/**"),
                        HandlerFunctions.http("http://localhost:8110"))
                .filter(CircuitBreakerFilterFunctions.circuitBreaker("circuit"))
                .filter(Bucket4jFilterFunctions.rateLimit(c -> c.setCapacity(5)
                        .setPeriod(Duration.ofMinutes(1))
                        .setKeyResolver(request -> request.remoteAddress().orElse(new InetSocketAddress("unknown", 0)).toString())
                        .setStatusCode(HttpStatus.TOO_MANY_REQUESTS)
                        .setHeaderName(HttpStatus.TOO_MANY_REQUESTS.name())
                ))
                .filter(JWTFilterFunctions())
                .before(BeforeFilterFunctions.addRequestHeader("gateway","ok"))
                .before(BeforeFilterFunctions.rewritePath("/(api|member)/(?<segment>.*)","/${segment}"))
                .before(serverRequest -> {
                    if (serverRequest.path().contains("/chat")) {
                        return ServerRequest.from(serverRequest)
                                .headers(httpHeaders -> httpHeaders.replace("Upgrade", List.of("websocket")))
                                .build();
                    }
                    return serverRequest;
                })
                .after(AfterFilterFunctions.removeResponseHeader("MEMBER_NUMBER"))
                .build();
    }

    private HandlerFilterFunction<ServerResponse, ServerResponse> JWTFilterFunctions() {
        return (request, next) -> {
            String jwt = request.headers().firstHeader("MY_APP_TOKEN");
            if (StringUtils.isBlank(jwt)) {
                return next.handle(request);
            }

            try {
                SignedJWT signedJWT = MyAppJwtUtils.parseJWT(jwt);
                List<Map<String, String>> loginInfos = redisTemplate.opsForValue().get(signedJWT.getJWTClaimsSet().getClaim("email"));

                assert loginInfos != null;
                Map<String, String> loginMap = loginInfos.stream()
                        .filter(map -> map.get("JSESSIONID").equals(request.headers().firstHeader("JSESSIONID")))
                        .findFirst()
                        .orElseThrow(AuthenticationException::new);

                ServerRequest newRequest = ServerRequest.from(request)
                        .headers(httpHeaders -> httpHeaders.add("MEMBER_NUMBER", loginMap.getOrDefault("NUMBER", "-1")))
                        .headers(httpHeaders -> httpHeaders.add("MEMBER_NICK_NAME",loginMap.getOrDefault("NICK_NAME", "")))
                        .build();

                return next.handle(newRequest);
            } catch (Exception e) {
                return ServerResponse.badRequest().build();
            }
        };
    }

    @Bean
    public AsyncProxyManager<String> proxyManager(@Qualifier("BucketRedisClient") RedisClient redisClient) {
        StatefulRedisConnection<String, byte[]> connect = redisClient.connect(RedisCodec.of(StringCodec.UTF8, ByteArrayCodec.INSTANCE));
        var proxyManagerBuilder = Bucket4jLettuce
                .casBasedBuilder(connect)
                .expirationAfterWrite(ExpirationAfterWriteStrategy.basedOnTimeForRefillingBucketUpToMax(Duration.ofSeconds(10)))
                .build();
        return proxyManagerBuilder.asAsync();
    }

}
