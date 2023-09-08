package my.application.api.controllers.google;

import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.repository.CrudRepository;

@EnableRedisRepositories
public interface CredentialTokenRepository extends CrudRepository<CredentialToken, String> {
}
