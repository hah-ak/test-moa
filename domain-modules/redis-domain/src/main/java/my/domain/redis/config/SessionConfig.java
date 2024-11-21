package my.domain.redis.config;

import my.domain.redis.RedisSessionTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.session.data.redis.config.annotation.SpringSessionRedisConnectionFactory;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisIndexedHttpSession;

@Configuration
@PropertySource("classpath:domain-redis-${spring.profiles.active:local}.properties")
@EnableRedisIndexedHttpSession(redisNamespace = "online:session")
public class SessionConfig {

    @Bean("redisSessionProperties")
    @ConfigurationProperties(prefix = "application.db.redis.session")
    public RedisConfig.RedisConfigurationProperties redisConfigurationProperties() {
        return new RedisConfig.RedisConfigurationProperties();
    }

    @Bean("redisSessionFactory")
    @SpringSessionRedisConnectionFactory
    public LettuceConnectionFactory lettuceConnectionFactory(
            @Qualifier("redisSessionProperties") RedisConfig.RedisConfigurationProperties properties) {
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
        configuration.setPassword(properties.getPassword());
        configuration.setDatabase(properties.getDatabase());
        configuration.setPort(properties.getPort());
        configuration.setHostName(properties.getHost());
        return new LettuceConnectionFactory(configuration);
    }

    @Bean("redisSessionTemplate")
    public <K,V> RedisSessionTemplate<K, V> redisTemplate(
            @Qualifier("redisSessionFactory") LettuceConnectionFactory lettuceConnectionFactory) {
        RedisSessionTemplate<K, V> kvRedisTemplate = new RedisSessionTemplate<>();
        kvRedisTemplate.setConnectionFactory(lettuceConnectionFactory);
        return kvRedisTemplate;
    }
}
