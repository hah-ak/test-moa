package my.application.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisReactiveAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, RedisReactiveAutoConfiguration.class }) // 레디스만 사용하나 jpa 프로젝트 종속성이 있어서 자동으로 호출중
public class GatewayApplicationMain {
    public static void main(String[] args) {
        SpringApplication.run(GatewayApplicationMain.class, args);
    }
}
