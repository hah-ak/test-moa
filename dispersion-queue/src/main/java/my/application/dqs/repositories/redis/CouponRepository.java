package my.application.dqs.repositories.redis;

import my.application.dqs.entities.coupon.CouponEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CouponRepository extends CrudRepository<CouponEntity, Long> {
}
