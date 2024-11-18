package my.application.user.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health")
public class ServerCheck {
    @GetMapping("/check")
    public String check() {
        return "user server ok";
    }
}
