package my.application.api.services.coupon;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.application.api.producer.coupon.CouponCreateProducer;
import my.application.api.repositories.redis.CouponRepository;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class CouponService {

    private final CouponRepository couponRepository;
    private final CouponRedisCustom couponRedisCustom;
    private final CouponCreateProducer couponCreateProducer;
    public void issue(Long userId) {

//        long count = couponRepository.count();

        if(couponRedisCustom.add(userId) != 1) return ;

        Long count = couponRedisCustom.increase();

        if (count > 100) {
            return;
        }

//        couponRepository.save(new CouponEntity(userId));
        couponCreateProducer.create(userId);
    }
}
