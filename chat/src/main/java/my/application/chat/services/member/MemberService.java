package my.application.chat.services.member;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.temporal.TemporalAmount;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void getMember() throws ExecutionException, InterruptedException {
        try {
            ProducerRecord<String, String> stringStringProducerRecord = new ProducerRecord<>("member.request.data", "asdf@asdf.com");
            CompletableFuture<SendResult<String, String>> send = kafkaTemplate.send(stringStringProducerRecord);
            SendResult<String, String> stringStringSendResult = send.get(10, TimeUnit.SECONDS);
            ConsumerRecord<String, String> receive = kafkaTemplate.receive("member.response.data", 0, 0, Duration.ZERO);
            System.out.println(receive.value());
        } catch (Exception e) {

        }


//        return new MemberEntity();
    }
}
