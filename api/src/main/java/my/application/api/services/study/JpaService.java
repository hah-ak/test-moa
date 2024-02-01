package my.application.api.services.study;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import my.application.api.entities.study.StudyMember;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JpaService {

    @Qualifier("studyLocalContainerEntityManagerFactoryBean")
    private final EntityManagerFactory entityManagerFactory;
    public void first() {

//        EntityManagerFactory hello = Persistence.createEntityManagerFactory("hello",properties);
        EntityManager entityManager = entityManagerFactory.createEntityManager(); // 트랜젝션마다 만들어줘야함. 일종의 커넥션
        //---
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        try {
//            List<StudyMember> resultList = entityManager.createQuery("select s from StudyMember as s", StudyMember.class).getResultList();
            StudyMember jpaMember = new StudyMember();
            jpaMember.setId(1L);
            jpaMember.setName("test");
            entityManager.persist(jpaMember); // persist context 에만 저장  =>(db와 스프링 사이에 존재한다보면됨.)
            //persist context 의 장점
            // 1차캐시 (셀렉트 혹은 내부에서 처음으로 만들어진 엔티티등 기록)===> 트랜잭션 내부에서 1차캐시가됨. 트랜잭션단위에서 같은값에 대해서 db에서 지속적 조회를 하지 않고 값을 가져옴.
            // 엔티티 동일성 보장 ===> 같은 정보를 find해서 가져올때 완전히 같은 객체(해쉬값까지) 를 줌.
            // 쓰기 지연 sql 저장소 ===> 최종적으로 커밋단계에서 그동안 만들어 놓은 쿼리를 실행.(save등 디비에 변화를 주는 jpa들의 쿼리)
            // 자바 컬렉션같이 레퍼런스 값의 변화를 주면 그 자체가 업데이트 쿼리가 되어 persist가 필요 없음.  ==> commit 시점에 결국 모든변경(dirty checking)에 대해서 업데이트, 인서트를 해준단 소리
            // flush를 직접호출하거나, jpql, commit 호출 시 db에 반영 ( jpql, commit내부에 flush가 있음)
            // jpa에서 최종적으로 변경감지하고 상태를 기억하는 것은 flush후 1차 캐시에 있는 스냅샷들과 비교후 쓰기지연 저장소에 있는 쿼리들 수행함으로서 진행.

            // jpql 객체를 대상으로 검색 즉 객체를 테이블처럼 검색하는 쿼리임.
            // 동적쿼리 짜기가 힘듦
            entityManager.createQuery("select m from StudyMember m where m.role = 'ADMIN'");

            //criteria
            // 동적쿼리는 짜긴 좋은데 그냥 힘듦.
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<StudyMember> query = criteriaBuilder.createQuery(StudyMember.class);

            Root<StudyMember> from = query.from(StudyMember.class);

            CriteriaQuery<StudyMember> where = query.select(from).where(criteriaBuilder.equal(from.get("name"), "test"));
            List<StudyMember> resultList = entityManager.createQuery(where).getResultList();
            transaction.commit(); // db에 직접적으로 실행하는 단계(쿼리실행)
        } catch (Exception e) {
            transaction.rollback();
        } finally {
            entityManager.close();
        }



        //---

    }
}
