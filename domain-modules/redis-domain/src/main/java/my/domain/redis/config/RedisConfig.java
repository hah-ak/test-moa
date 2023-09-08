package my.domain.redis.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;


@Configuration
@PropertySource("classpath:domain-redis-${spring.profiles.active}.properties")
@EntityScan("my.domain.redis.entities")
@EnableRedisRepositories(basePackages = {"my.domain.redis.repositories"})
public class RedisConfig {

    @Getter
    @Setter
    public static class RedisConfigurationProperties {
        private Integer database;
        private String password;
        private String host;
        private Integer port;
    }

    @Bean
    @ConfigurationProperties(prefix = "application.db.redis.zero")
    public RedisConfigurationProperties redisConfigurationProperties() {
        return new RedisConfigurationProperties();
    }

    @Bean
    public LettuceConnectionFactory lettuceConnectionFactory(RedisConfigurationProperties properties) {
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
        configuration.setPassword(properties.getPassword());
        configuration.setDatabase(properties.getDatabase());
        configuration.setPort(properties.getPort());
        configuration.setHostName(properties.getHost());
        return new LettuceConnectionFactory(configuration);
    }

    @Bean
    public <K,V> RedisTemplate<K, V> redisTemplate(LettuceConnectionFactory lettuceConnectionFactory) {
        RedisTemplate<K, V> kvRedisTemplate = new RedisTemplate<>();
        kvRedisTemplate.setConnectionFactory(lettuceConnectionFactory);
        return kvRedisTemplate;
    }

}
