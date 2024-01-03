package my.application.api.repositories.member;

import my.application.api.entities.MemberJoinRoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//generic설정시 그냥 설정해버리면 object도 entity로 인식하려해서 문제 생김.
@Repository
public interface MemberJoinRoomRepository extends JpaRepository<MemberJoinRoomEntity, Integer> {
}
