package my.domain.redis.repositories.coupon;

import my.domain.redis.entities.coupon.CouponFailedEvent;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CouponFailedRepository extends CrudRepository<CouponFailedEvent, Long> {
}
