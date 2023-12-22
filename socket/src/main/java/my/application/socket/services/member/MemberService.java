package my.application.socket.services.member;

import lombok.RequiredArgsConstructor;
import my.domain.mysql.entities.MemberEntity;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final KafkaTemplate<MemberEntity, String> memberEntityStringKafkaTemplate;

    public void getMember() throws ExecutionException, InterruptedException {
        SendResult<String , String> memberEntityStringSendResult1 = kafkaTemplate.send("member.data", "asdf@asdf.com")
                .whenCompleteAsync((memberEntityStringSendResult, throwable) -> {
                    ConsumerRecord<MemberEntity, String> receive = memberEntityStringKafkaTemplate.receive("member.data", 0, 1000, Duration.ZERO);
                    System.out.println(receive.key().getAuthority());
                })
                .get();


//        return new MemberEntity();
    }
}
