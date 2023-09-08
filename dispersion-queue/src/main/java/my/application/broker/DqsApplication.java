package my.application.broker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"my.application.broker", "my.domain.redis"})
public class DqsApplication {

	public static void main(String[] args) {
		SpringApplication.run(DqsApplication.class, args);
	}

}
