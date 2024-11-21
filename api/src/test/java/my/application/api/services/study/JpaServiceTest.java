package my.application.api.services.study;

import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles({"local"})
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