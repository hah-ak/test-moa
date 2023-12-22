package my.application.socket.services.member;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.ExecutionException;

@SpringBootTest(properties = {"spring.profiles.active=local"})
class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Test
    void getMember() throws ExecutionException, InterruptedException {

        memberService.getMember();
    }
}