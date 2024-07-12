package my.application.gateway.config;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import my.domain.redis.config.RedisConfig;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

@Configuration
public class BucketRedisConfig {

    @Bean("BucketRedisConfig")
    @ConfigurationProperties(prefix = "application.db.redis.one")
    public RedisConfig.RedisConfigurationProperties redisConfigurationProperties() {
        return new RedisConfig.RedisConfigurationProperties();
    }

    @Bean("BucketLettuceConnectionFactory")
    public LettuceConnectionFactory lettuceConnectionFactory(@Qualifier("BucketRedisConfig") RedisConfig.RedisConfigurationProperties properties) {
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
        configuration.setPassword(properties.getPassword());
        configuration.setDatabase(properties.getDatabase());
        configuration.setPort(properties.getPort());
        configuration.setHostName(properties.getHost());
        return new LettuceConnectionFactory(configuration);
    }

    @Bean("BucketRedisClient")
    public RedisClient redisClient(@Qualifier("BucketRedisConfig") RedisConfig.RedisConfigurationProperties properties) {
        return RedisClient.create(RedisURI.Builder
                .redis(properties.getHost(), properties.getPort())
                .withDatabase(properties.getDatabase())
                .withPassword(properties.getPassword())
                .withLibraryName("application")
                .build()
        );
    }
}
