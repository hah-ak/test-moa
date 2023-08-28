package my.application.security.config;

import my.domain.redis.config.RedisConfig;
import org.springframework.beans.factory.annotation.Qualifier;
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
@EntityScan(basePackages = {"my.application.security.entities.redis"})
@EnableRedisRepositories(basePackages = {"my.application.security.repositories.redis"})
public class SecurityRedisConfig {

    @Bean("securityRedisProperties")
    @ConfigurationProperties(prefix = "application.db.redis.security")
    public RedisConfig.RedisConfigurationProperties redisConfigurationProperties() {
        return new RedisConfig.RedisConfigurationProperties();
    }

    @Bean("securityRedisLettuceFactory")
    public LettuceConnectionFactory lettuceConnectionFactory(@Qualifier("securityRedisProperties") RedisConfig.RedisConfigurationProperties properties) {
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
        configuration.setPassword(properties.getPassword());
        configuration.setDatabase(properties.getDatabase());
        configuration.setPort(properties.getPort());
        configuration.setHostName(properties.getHost());
        return new LettuceConnectionFactory(configuration);
    }
}
