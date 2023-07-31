package my.application.myApp.consumer.coupon;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.application.myApp.entities.coupon.CouponEntity;
import my.application.myApp.entities.coupon.CouponFailedEvent;
import my.application.myApp.repositories.redis.CouponFailedRepository;
import my.application.myApp.repositories.redis.CouponRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CouponCreatedConsumer {

    private final CouponRepository couponRepository;
    private final CouponFailedRepository couponFailedRepository;
    @KafkaListener(topics = "coupon_create", groupId = "group_1")
    public void listener(Long userId) {
        try {
            couponRepository.save(new CouponEntity(userId));
        } catch (Exception e) {
            log.error("{}",e.getMessage());
            couponFailedRepository.save(new CouponFailedEvent(userId));
        }
    }
}
