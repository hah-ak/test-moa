package my.application.member.repositories.mysql.member;


import my.application.member.entities.mysql.member.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity,Integer> {

    MemberEntity findByMemNo(Integer memNo);
    MemberEntity findById(String memId);
}
