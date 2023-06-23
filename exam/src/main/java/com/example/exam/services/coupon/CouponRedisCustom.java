package com.example.exam.services.coupon;

import com.example.exam.entities.redis.CouponEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CouponRedisCustom {

    private final RedisTemplate<String, String> redisTemplate;

    public Long increase() {
        return redisTemplate.opsForValue().increment("coupon_number");
    }
}
