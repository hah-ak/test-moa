package my.application.kafkaconsumers.consumer.coupon;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class CouponCreatedConsumer {

    @KafkaListener(topics = "coupon_create", groupId = "group_id")
    public void listener(Long userId) {

    }
}
