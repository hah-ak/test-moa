package my.application.common.repositories.mysql;


import my.application.common.entities.mysql.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity,Integer> {

    MemberEntity findByMemNo(Integer memNo);
    MemberEntity findById(String memId);
}
