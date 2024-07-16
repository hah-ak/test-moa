package my.application.member;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"my.domain.redis","my.domain.mysql","my.application.member.entities.mysql"})
@EnableRedisRepositories(basePackages = {"my.application.member.repositories.google"})
public class MemberApplicationMain {
    public static void main(String[] args) {
        SpringApplication.run(MemberApplicationMain.class, args);
    }
}