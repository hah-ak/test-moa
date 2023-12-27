package my.application.socket.services.member;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void getMember() throws ExecutionException, InterruptedException {
        try {
            ProducerRecord<String, String> stringStringProducerRecord = new ProducerRecord<>("member.request.data", "asdf@asdf.com");
        } catch (Exception e) {

        }



//        return new MemberEntity();
    }
}
