package my.application.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@SpringBootApplication(scanBasePackages = {"my.domain.redis","my.domain.mysql", "my.application.user"})
@EnableRedisRepositories(basePackages = {"my.application.user.repositories.google"})
public class UserApplicationMain {
    public static void main(String[] args) {
        SpringApplication.run(UserApplicationMain.class, args);
    }
}