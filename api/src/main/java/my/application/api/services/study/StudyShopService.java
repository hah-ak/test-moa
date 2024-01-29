package my.application.api.services.study;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import lombok.RequiredArgsConstructor;
import my.application.api.entities.study.StudyShopMember;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudyShopService {

    @Qualifier("studyLocalContainerEntityManagerFactoryBean")
    private final EntityManagerFactory entityManagerFactory;

    public void service() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            StudyShopMember studyShopMember = new StudyShopMember("wang", "gwnagju","jinjegil","61712");
            entityManager.persist(studyShopMember);

            transaction.commit();

            StudyShopMember studyShopMember1 = entityManager.find(StudyShopMember.class, studyShopMember.getId());


        } catch (Exception e) {
            transaction.rollback();
        } finally {
            entityManager.close();
            entityManagerFactory.close();
        }
    }
}
