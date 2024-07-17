package my.application.member.config;

import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.json.gson.GsonFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Configuration
public class SecurityPackageConfig {
    // jar파일 생성시 파일경로가 jar:file: 경로가 되어 file reader 를 사용할 때 제대로 읽어오지 못해서 리더로 바꿔야함.
    @Bean
    @Primary
    public GoogleClientSecrets googleClientSecrets(ApplicationContext applicationContext) throws IOException {
        Resource resource = applicationContext.getResource("classpath:/client_secret.json");
        return GoogleClientSecrets.load(GsonFactory.getDefaultInstance(), new BufferedReader(new InputStreamReader(resource.getInputStream())));
    }

}
