package my.application.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class) // 레디스만 사용하나 jpa 프로젝트 종속성이 있어서 자동으로 호출중
@ComponentScan(basePackages = {"my.domain.redis", "my.application.gateway"})
public class GatewayApplicationMain {
    public static void main(String[] args) {
        SpringApplication.run(GatewayApplicationMain.class, args);
    }
}
