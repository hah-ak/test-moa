package my.application.chat.repositories.mysql;

import my.application.chat.entities.mysql.RoomEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends CrudRepository<RoomEntity, Long> {
}
