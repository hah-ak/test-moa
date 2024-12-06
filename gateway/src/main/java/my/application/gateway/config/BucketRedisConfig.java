package my.application.gateway.config;

import io.github.bucket4j.distributed.ExpirationAfterWriteStrategy;
import io.github.bucket4j.distributed.proxy.AsyncProxyManager;
import io.github.bucket4j.redis.lettuce.Bucket4jLettuce;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.codec.ByteArrayCodec;
import io.lettuce.core.codec.RedisCodec;
import io.lettuce.core.codec.StringCodec;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

import static my.application.gateway.config.FluxGateWayConfig.RedisConfigurationProperties;

@Configuration
@PropertySource("classpath:domain-redis-${spring.profiles.active:local}.properties")
public class BucketRedisConfig {

    @Bean("BucketRedisConfig")
    @ConfigurationProperties(prefix = "application.db.redis.bucket")
    public RedisConfigurationProperties redisConfigurationProperties() {
        return new RedisConfigurationProperties();
    }

    @Bean("BucketLettuceConnectionFactory")
    public LettuceConnectionFactory lettuceConnectionFactory(@Qualifier("BucketRedisConfig") RedisConfigurationProperties properties) {
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
        configuration.setPassword(properties.getPassword());
        configuration.setDatabase(properties.getDatabase());
        configuration.setPort(properties.getPort());
        configuration.setHostName(properties.getHost());
        return new LettuceConnectionFactory(configuration);
    }

    @Bean("BucketRedisClient")
    public RedisClient redisClient(@Qualifier("BucketRedisConfig") RedisConfigurationProperties properties) {
        return RedisClient.create(RedisURI.Builder
                .redis(properties.getHost(), properties.getPort())
                .withDatabase(properties.getDatabase())
                .withPassword(properties.getPassword())
                .withLibraryName("application")
                .build()
        );
    }

    @Bean
    public AsyncProxyManager<String> proxyManager(@Qualifier("BucketRedisClient") RedisClient redisClient) {
        StatefulRedisConnection<String, byte[]> connect = redisClient.connect(RedisCodec.of(StringCodec.UTF8, ByteArrayCodec.INSTANCE));
        var proxyManagerBuilder = Bucket4jLettuce
                .casBasedBuilder(connect)
                .expirationAfterWrite(ExpirationAfterWriteStrategy.basedOnTimeForRefillingBucketUpToMax(Duration.ofSeconds(10)))
                .build();
        return proxyManagerBuilder.asAsync();
    }

    @Bean("myReactiveRedisTemplate")
    @Primary
    public ReactiveStringRedisTemplate redisTemplate(
            @Qualifier("BucketLettuceConnectionFactory") ReactiveRedisConnectionFactory lettuceConnectionFactory) {
        return new ReactiveStringRedisTemplate(lettuceConnectionFactory, RedisSerializationContext.string());
    }
}
