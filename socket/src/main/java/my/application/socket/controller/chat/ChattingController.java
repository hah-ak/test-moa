package my.application.socket.controller.chat;

import lombok.RequiredArgsConstructor;
import my.domain.mysql.entities.MemberEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;

@Controller
@MessageMapping("/chat")
@RequiredArgsConstructor
public class ChattingController {
    @MessageMapping("/room/{number}/enter")
    @SendTo("/chatting/room/{number}/enter-notice")
    public String room(@DestinationVariable("number") int number, Authentication authentication) {
        return "enter";
    }

    @MessageMapping("/room/{number}")
    @SendTo("/chatting/room/{number}/talk-message")
    public String room2(@DestinationVariable("number") int number) {
        return "??";
    }


}
