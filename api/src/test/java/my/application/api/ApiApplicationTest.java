package my.application.api;

import my.domain.mysql.entities.MemberEntity;
import my.domain.mysql.repositories.member.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
@SpringBootTest(properties = {"spring.profiles.active=dev"})
class ApiApplicationTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    void memtest() {
        MemberEntity byId = memberRepository.findById("asdf@asdf.asdf");
        System.out.println(byId.getId());
    }

}