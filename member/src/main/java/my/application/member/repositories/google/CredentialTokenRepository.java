package my.application.member.repositories.google;

import my.application.gateway.entities.google.CredentialToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CredentialTokenRepository extends CrudRepository<CredentialToken, String> {

}
