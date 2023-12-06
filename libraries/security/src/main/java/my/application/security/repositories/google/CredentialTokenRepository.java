package my.application.security.repositories.google;

import my.application.security.entities.google.CredentialToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CredentialTokenRepository extends CrudRepository<CredentialToken, String> {

}
