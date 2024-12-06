package my.application.api.services.study.jqpl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(properties = {"spring.profiles.active=local"})
class JpqlServiceTest {

    @Autowired
    JpqlService jpqlService;
//    @Test
    void querying() {
        jpqlService.querying();
    }
}