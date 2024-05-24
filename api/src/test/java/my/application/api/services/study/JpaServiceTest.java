package my.application.api.services.study;

import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest()
class JpaServiceTest {

    @SpyBean
    EntityManagerFactory entityManagerFactory;

    @Autowired
    JpaService jpaService;
    @Test
    void first() {
        jpaService.first();
    }
}