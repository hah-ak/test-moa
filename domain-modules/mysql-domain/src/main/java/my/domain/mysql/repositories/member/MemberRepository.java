package my.domain.mysql.repositories.member;


import my.domain.mysql.entities.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity,Integer> {

    MemberEntity findByMemNo(Integer memNo);
    MemberEntity findById(String memId);
}
