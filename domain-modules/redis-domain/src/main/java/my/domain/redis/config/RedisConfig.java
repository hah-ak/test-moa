package my.domain.redis.config;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import lombok.Getter;
import lombok.Setter;
import my.domain.redis.RedisCommonTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;


@Configuration
@PropertySource("classpath:domain-redis-${spring.profiles.active:local}.properties")
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

    @Bean("redisCommonProperties")
    @ConfigurationProperties(prefix = "application.db.redis.common")
    public RedisConfigurationProperties redisConfigurationProperties() {
        return new RedisConfigurationProperties();
    }

    @Bean("redisCommonFactory")
    @Primary
    public LettuceConnectionFactory lettuceConnectionFactory(
            @Qualifier("redisCommonProperties") RedisConfigurationProperties properties) {
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
        configuration.setPassword(properties.getPassword());
        configuration.setDatabase(properties.getDatabase());
        configuration.setPort(properties.getPort());
        configuration.setHostName(properties.getHost());
        return new LettuceConnectionFactory(configuration);
    }

    @Bean("redisCommonClient")
    public RedisClient redisClient(
            @Qualifier("redisCommonProperties") RedisConfigurationProperties properties) {
        return RedisClient.create(RedisURI.Builder
                        .redis(properties.getHost(), properties.getPort())
                        .withDatabase(properties.getDatabase())
                        .withPassword(properties.getPassword())
                        .withLibraryName("application")
                .build()
        );
    }

    @Bean
    public <K,V> RedisCommonTemplate<K, V> redisTemplate(
            @Qualifier("redisCommonFactory") LettuceConnectionFactory lettuceConnectionFactory) {
        RedisCommonTemplate<K, V> kvRedisTemplate = new RedisCommonTemplate<>();
        kvRedisTemplate.setConnectionFactory(lettuceConnectionFactory);
        return kvRedisTemplate;
    }

}
