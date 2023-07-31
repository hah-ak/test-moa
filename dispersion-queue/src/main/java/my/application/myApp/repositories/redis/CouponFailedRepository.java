package my.application.myApp.repositories.redis;

import my.application.myApp.entities.coupon.CouponFailedEvent;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CouponFailedRepository extends CrudRepository<CouponFailedEvent, Long> {
}
