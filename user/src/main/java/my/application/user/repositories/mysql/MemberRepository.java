package my.application.user.repositories.mysql;


import my.application.user.entities.mysql.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity,Long> {

    MemberEntity findByMemNo(Long memNo);
    MemberEntity findById(String memId);
}
