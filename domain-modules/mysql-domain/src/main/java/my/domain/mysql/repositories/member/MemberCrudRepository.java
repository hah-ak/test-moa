package my.domain.mysql.repositories.member;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import my.domain.mysql.entities.MemberEntity;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
@RequiredArgsConstructor
public class MemberCrudRepository {

    private final EntityManager entityManager;
    public MemberEntity save(MemberEntity memberEntity) {
        entityManager.persist(memberEntity);
        return memberEntity;
    }
}
