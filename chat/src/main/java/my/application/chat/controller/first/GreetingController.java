package my.application.chat.controller.first;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Controller;


@Controller
//@MessageMapping("/hello")
public class GreetingController {
//    @SendTo("/topic/greetings")
//    public Greeting greeting(String messages) {
//        return new Greeting("hi" + messages);
//    }

//    @MessageMapping("/room")
//    public Greeting greetings() {
//        return new Greeting("romm hi");
//    }

    @KafkaListener(topics = "member.room.response.data", groupId = "group_1")
    public String room() {
        return "room";
    }
}
