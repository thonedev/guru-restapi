package com.example.reactor.web.client.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "spring.kafka")
@Data
public class KafkaProperties {
    private String bootstrapServers;
    private Consumer consumer;
    private Producer producer;
    private String topic1;
    private String topic2;

    @Data
    public static class Consumer{
        private String groupId;
        private String autoOffsetReset;
        private String keyDeserializer;
        private String valueDeserializer;
    }

    @Data
    public static class Producer{
        private String keySerializer;
        private String valueSerializer;
    }

}
