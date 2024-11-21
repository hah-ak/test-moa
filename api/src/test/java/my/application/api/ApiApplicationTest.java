package my.application.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;

@SpringBootTest(properties = {"spring.profiles.active=local"})
class ApiApplicationTest {

//    @Autowired
//    private MemberRepository memberRepository;

    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;

    @Test
    void memtest() {
//        MemberEntity byId = memberRepository.findById("asdf@asdf.asdf");
//        System.out.println(byId.getId());
//        if (kafkaTemplate != null) {
//            kafkaTemplate.send("member.request.data","asdf@asdf.asdf");
//        }
    }

}