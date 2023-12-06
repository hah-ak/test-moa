package my.domain.mysql.repositories.member;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import my.domain.mysql.entities.MemberEntity;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public class MemberDefaultRepository {
    @PersistenceContext
    private EntityManager em;

    public MemberEntity save(MemberEntity memberEntity) {
        em.persist(memberEntity);
        return memberEntity;
    }
}
