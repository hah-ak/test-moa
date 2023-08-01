package my.application.security.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainControllers {
    @GetMapping("/")
    public String index() {
        return "gogo";
    }

//    @GetMapping("/login-process")
//    public String loginProcess(@ModelAttribute UserLogin login) {
//
//    }
}
