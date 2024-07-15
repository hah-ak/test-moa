package my.application.chat.repositories.mysql;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomJoinMemberRepository extends CrudRepository<RoomJoinMemberRepository, Long> {
}
