package my.application.chat.repositories.mysql;


import my.application.chat.entities.mysql.RoomJoinMemberEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomJoinMemberRepository extends CrudRepository<RoomJoinMemberEntity, Long> {
    RoomJoinMemberEntity findByRoomIdAndMemberNumber(Long roomId, Long memberNumber);
}
