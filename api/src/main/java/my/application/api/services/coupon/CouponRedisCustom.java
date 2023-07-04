package my.application.api.services.coupon;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.BoundSetOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class CouponRedisCustom {

    private final RedisTemplate<String, String> redisTemplate;

    public Long increase() {
        return redisTemplate.opsForValue().increment("coupon_number");
    }

    public Long add(Long userId) {
        return redisTemplate.opsForSet().add("applied_user",userId.toString());
    }
}
