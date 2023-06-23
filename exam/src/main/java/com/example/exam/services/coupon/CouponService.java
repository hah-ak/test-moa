package com.example.exam.services.coupon;

import com.example.exam.entities.redis.CouponEntity;
import com.example.exam.repositories.redis.CouponRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class CouponService {

    private final CouponRepository couponRepository;
    private final CouponRedisCustom couponRedisCustom;
    public void issue(Long userId) {

//        long count = couponRepository.count();
        Long count = couponRedisCustom.increase();

        if (count > 100) {
            return;
        }

        couponRepository.save(new CouponEntity(userId));
    }
}
