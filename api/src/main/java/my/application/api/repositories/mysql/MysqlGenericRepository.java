package my.application.api.repositories.mysql;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MysqlGenericRepository<T,K> extends JpaRepository<T,K> {
}
