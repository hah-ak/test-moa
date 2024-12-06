package my.application.gateway.config;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.BucketConfiguration;
import io.github.bucket4j.distributed.AsyncBucketProxy;
import io.github.bucket4j.distributed.proxy.AsyncProxyManager;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.gateway.filter.ratelimit.AbstractRateLimiter;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.HashMap;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Component
@Primary
public class Bucket4jRateLimiter extends AbstractRateLimiter<Bucket4jRateLimiter.Config> {

    private final AsyncProxyManager<String> proxyManager;

    @Autowired
    public Bucket4jRateLimiter(AsyncProxyManager<String> proxyManager) {
        super(Config.class,"bucket4j-rate-limiter", null);
        this.proxyManager = proxyManager;
    }

    @Getter
    @Setter
    public static class Config {
        private int capacity = 4;
        private int refillTokens = 5;
        private Duration refillPeriod = Duration.ofMinutes(1);
    }

    @Override
    public Mono<Response> isAllowed(String routeId, String id) {
        Config config = getConfig().get(routeId);
        if (config == null) {
            return Mono.just(new Response(true, new HashMap<>()));
        }

        BucketConfiguration configuration = BucketConfiguration.builder()
                .addLimit(Bandwidth.builder().capacity(config.getCapacity()).refillIntervally(config.refillTokens, config.refillPeriod).build())
                .build();

        AsyncBucketProxy build = proxyManager.builder().build(id, () -> CompletableFuture.completedFuture(configuration));

        try {
            Boolean b = build.tryConsume(1).get();
            return Mono.just(new Response(b,new HashMap<>()));
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }

    }
}
