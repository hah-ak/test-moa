package my.application.security.repositories;

import my.application.security.entities.oauth.Client;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends CrudRepository<Client, String> {
    Optional<Client> findByClientId(String clientId);
}
