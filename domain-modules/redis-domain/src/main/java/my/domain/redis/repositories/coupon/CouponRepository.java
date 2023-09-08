package my.domain.redis.repositories.coupon;

import my.domain.redis.entities.coupon.CouponEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CouponRepository extends CrudRepository<CouponEntity, Long> {
}
