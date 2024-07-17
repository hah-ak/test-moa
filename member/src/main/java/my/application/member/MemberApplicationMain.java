package my.application.member;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@SpringBootApplication(scanBasePackages = {"my.domain.redis","my.domain.mysql","my.application.member"})
@EnableRedisRepositories(basePackages = {"my.application.member.repositories.google"})
public class MemberApplicationMain {
    public static void main(String[] args) {
        SpringApplication.run(MemberApplicationMain.class, args);
    }
}