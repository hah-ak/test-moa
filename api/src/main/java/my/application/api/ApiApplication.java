package my.application.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication(scanBasePackages = {"my.application.api", "my.domain.mysql", "my.domain.redis","web.core"}) //entity 및 enable repository  에 영향을 주지 않으므로 추가가 필요함.
public class ApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiApplication.class, args);
	}

}