package my.domain.redis.repositories.google;

import my.domain.redis.entities.google.CredentialToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CredentialTokenRepository extends CrudRepository<CredentialToken, String> {
}
