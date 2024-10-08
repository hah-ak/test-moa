package my.application.chat.config;

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
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@PropertySource("classpath:domain-redis-${spring.profiles.active}.properties")
@EntityScan("my.domain.redis.entities")
@EnableRedisRepositories(basePackages = {"my.application.chat.repositories.redis"})
public class ChatRedisConfig {

    @Bean("redisChatRoomProperties")
    @ConfigurationProperties(prefix = "application.db.redis.chat.room")
    public RedisConfig.RedisConfigurationProperties redisConfigurationProperties() {
        return new RedisConfig.RedisConfigurationProperties();
    }

    @Bean("redisChatRoomFactory")
    public LettuceConnectionFactory lettuceConnectionFactory(
            @Qualifier("redisChatRoomProperties") my.domain.redis.config.RedisConfig.RedisConfigurationProperties properties) {
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
        configuration.setPassword(properties.getPassword());
        configuration.setDatabase(properties.getDatabase());
        configuration.setPort(properties.getPort());
        configuration.setHostName(properties.getHost());
        return new LettuceConnectionFactory(configuration);
    }

//    @Bean
//    public <K, V> RedisTemplate<K, V> redisTemplate(
//            @Qualifier("redisChatRoomFactory") LettuceConnectionFactory lettuceConnectionFactory) {
//        RedisTemplate<K, V> kvRedisTemplate = new RedisTemplate<>();
//        kvRedisTemplate.setConnectionFactory(lettuceConnectionFactory);
//        kvRedisTemplate.setValueSerializer(StringRedisSerializer.UTF_8);
//        kvRedisTemplate.setKeySerializer(StringRedisSerializer.UTF_8);
//        kvRedisTemplate.setHashKeySerializer(StringRedisSerializer.UTF_8);
//        kvRedisTemplate.setHashValueSerializer(StringRedisSerializer.UTF_8);
//        kvRedisTemplate.setStringSerializer(StringRedisSerializer.UTF_8);
//        return kvRedisTemplate;
//    }
}
