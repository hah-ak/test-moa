package my.application.api.controllers.coupon;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.domain.redis.entities.coupon.CouponEntity;
import my.domain.redis.entities.coupon.CouponFailedEvent;
import my.domain.redis.repositories.coupon.CouponFailedRepository;
import my.domain.redis.repositories.coupon.CouponRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CouponCreated {

    private final CouponRepository couponRepository;
    private final CouponFailedRepository couponFailedRepository;
    @KafkaListener(topics = "coupon_create", groupId = "group_1", autoStartup = "false")
    public void listener(Long userId) {
        try {
            couponRepository.save(new CouponEntity(userId));
        } catch (Exception e) {
            log.error("{}",e.getMessage());
            couponFailedRepository.save(new CouponFailedEvent(userId));
        }
    }
}
