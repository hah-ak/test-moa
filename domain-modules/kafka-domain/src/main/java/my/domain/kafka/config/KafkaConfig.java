package my.domain.kafka.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.LongSerializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.core.*;
import web.core.util.EnvironmentValues;

import java.util.HashMap;

@Slf4j
@PropertySource("classpath:kafka-domain-${spring.profiles.active}.properties")
@EnableKafka
@Configuration
public class KafkaConfig {

    @Bean
    public ProducerFactory<String, Long> producerFactory() {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "myApp_kafka:9092");
        hashMap.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        hashMap.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, LongSerializer.class);
        return new DefaultKafkaProducerFactory<>(hashMap);
    }
    @Bean
    public KafkaTemplate<?, ?> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    @Bean
    @Primary
    public ConsumerFactory<String, Long> consumerFactory() {
        HashMap<String, Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,"myApp_kafka:9092");
        config.put(ConsumerConfig.GROUP_ID_CONFIG,"group_1");
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, LongDeserializer.class);
        return new DefaultKafkaConsumerFactory<>(config);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Long> kafkaListenerContainerFactory(ConsumerFactory<String, Long> consumerFactory) {
        ConcurrentKafkaListenerContainerFactory<String, Long> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        return factory;
    }
}
