package my.application.gateway.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@Slf4j
@RequiredArgsConstructor
public class RateLimitService {

    @Bean
    public DefaultRedisScript<Boolean> ratelimitScript() {
        DefaultRedisScript<Boolean> booleanDefaultRedisScript = new DefaultRedisScript<>();
        booleanDefaultRedisScript.setLocation(new ClassPathResource("rate-limiter.lua"));
        booleanDefaultRedisScript.setResultType(Boolean.class);
        return booleanDefaultRedisScript;
    }
    private final RedisTemplate<String, String> redisTemplate;
    private final DefaultRedisScript<Boolean> ratelimitScript = ratelimitScript();



    public boolean isAllowed(String key, int maxRequests, int windowSeconds) {
        return Boolean.TRUE.equals(redisTemplate.execute(
                ratelimitScript,
                Collections.singletonList(key),
                String.valueOf(maxRequests),
                String.valueOf(windowSeconds),
                String.valueOf(System.currentTimeMillis() / 1000)
                ));
    }

}
