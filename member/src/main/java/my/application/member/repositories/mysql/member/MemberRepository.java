package my.application.member.repositories.mysql.member;


import my.application.member.entities.mysql.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity,Long> {

    MemberEntity findByMemNo(Long memNo);
    MemberEntity findById(String memId);
}
