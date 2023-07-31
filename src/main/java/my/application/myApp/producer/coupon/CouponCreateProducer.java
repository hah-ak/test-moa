package my.application.myApp.producer.coupon;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CouponCreateProducer {

    private final KafkaTemplate<String, Long> kafkaTemplate;

    public void create(Long userid) {
        kafkaTemplate.send("coupon_create", userid);
    }
}
