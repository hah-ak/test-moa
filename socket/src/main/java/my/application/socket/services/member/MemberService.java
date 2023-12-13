package my.application.socket.services.member;

import lombok.RequiredArgsConstructor;
import my.domain.mysql.entities.MemberEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final KafkaTemplate<MemberEntity, String> kafkaTemplate;

    public MemberEntity getMember() throws ExecutionException, InterruptedException {
        SendResult<MemberEntity, String> memberEntityStringSendResult1 = kafkaTemplate.send("member/data", "asdf@asdf.com")
                .get();

        return memberEntityStringSendResult1.getProducerRecord().key();
    }
}
