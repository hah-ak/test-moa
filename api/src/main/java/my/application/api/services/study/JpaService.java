package my.application.api.services.study;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
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
            entityManager.persist(jpaMember); // persist context 에만 저장
            //persist context 의 장점
            // 1차캐시 ===>
            transaction.commit(); // db에 직접적으로 실행하는 단계(쿼리실행)
        } catch (Exception e) {
            transaction.rollback();
        } finally {
            entityManager.close();
        }



        //---

    }
}
