package my.application.gateway.repositories.mysql.member;


import my.application.gateway.entities.mysql.member.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity,Integer> {

    MemberEntity findByMemNo(Integer memNo);
    MemberEntity findById(String memId);
}
