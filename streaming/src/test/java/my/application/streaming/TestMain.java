package my.application.streaming;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;

@SpringBootTest(properties = {"spring.profiles.active=local"})
public class TestMain {

        @Autowired
        KafkaTemplate<String, String> kafkaTemplate;

        @Test
        void memtest() {
            kafkaTemplate.send("member.request.data","asdf@asdf.asdf");
}


}
