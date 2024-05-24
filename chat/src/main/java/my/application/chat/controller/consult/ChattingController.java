package my.application.chat.controller.consult;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
@MessageMapping("/chat")
@RequiredArgsConstructor
public class ChattingController {
    @MessageMapping("/room/{number}/enter")
    @SendTo("/chatting/room/{number}/enter-notice")
    public String room(@DestinationVariable("number") int number) {
        return "enter";
    }

    @MessageMapping("/room/{number}")
    @SendTo("/chatting/room/{number}/talk-message")
    public String room2(@DestinationVariable("number") int number) {
        return "??";
    }

}
