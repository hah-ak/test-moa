package my.application.dqs.entities.coupon;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@Setter
@RedisHash("coupon_failed")
public class CouponFailedEvent {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    public CouponFailedEvent(Long userId) {
        this.userId = userId;
    }
}
