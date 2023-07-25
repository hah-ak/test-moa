package my.application.api.repositories.mysql;

import my.application.api.entities.mysql.MemberJoinRoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//generic설정시 그냥 설정해버리면 object도 entity로 인식하려해서 문제 생김.
@Repository
public interface MemberJoinRoomRepository extends JpaRepository<MemberJoinRoomEntity, Integer> {
}
