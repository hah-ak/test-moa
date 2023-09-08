package my.servers.oauth2server.repositories.redis;

import my.application.security.entities.redis.Client;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends CrudRepository<Client, String> {
    Optional<Client> findByClientId(String clientId);
}
