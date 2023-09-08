package my.application.socket.controller.first;

import my.application.socket.entities.first.Greeting;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class GreetingController {
    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting greeting(String messages) {
        return new Greeting("hi" + messages);
    }

//    @MessageMapping("/room")
//    public Greeting greetings() {
//        return new Greeting("romm hi");
//    }
}
