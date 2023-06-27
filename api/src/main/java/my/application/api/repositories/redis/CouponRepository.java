package my.application.api.repositories.redis;

import my.application.api.entities.redis.CouponEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CouponRepository extends CrudRepository<CouponEntity, Long> {
}
