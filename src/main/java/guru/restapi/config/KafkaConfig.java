package guru.restapi.config;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverOptions;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderOptions;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class KafkaConfig {

    private KafkaProperties kafkaProperties;

    @Value("${spring.kafka.properties.sasl.jaas.config}")
    String saslJaasConfig;

    @Value("${client.id}")
    String clientId;

    @Value("${spring.kafka.properties.session.timeout.ms}")
    Integer sessionTimeout;

    @Bean
    public KafkaReceiver<String, JsonNode> kafkaReceiver(KafkaProperties props) {
        Map<String, Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, props.getBootstrapServers());
        config.put(ConsumerConfig.GROUP_ID_CONFIG, props.getConsumer().getGroupId());
        config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, props.getConsumer().getAutoOffsetReset());
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, props.getConsumer().getKeyDeserializer());
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, props.getConsumer().getValueDeserializer());
        config.put(ConsumerConfig.CLIENT_ID_CONFIG, clientId);
        config.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, sessionTimeout);
        config.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        config.put(JsonDeserializer.VALUE_DEFAULT_TYPE, JsonNode.class.getName());
        config.put(JsonDeserializer.USE_TYPE_INFO_HEADERS, false);
        config.put("security.protocol", "SASL_SSL");
        config.put("sasl.mechanism", "PLAIN");
        config.put("sasl.jaas.config", saslJaasConfig);


        ReceiverOptions<String, JsonNode> options = ReceiverOptions.<String, JsonNode>create(config)
                .subscription(Collections.singleton(props.getTopic1())); // ou topic2 etc.

        return KafkaReceiver.create(options);
    }

    @Bean
    public KafkaSender<String, JsonNode> kafkaSender(KafkaProperties props) {
        Map<String, Object> config = new HashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, props.getBootstrapServers());
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, props.getProducer().getKeySerializer());
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, props.getProducer().getValueSerializer());
        config.put(ProducerConfig.CLIENT_ID_CONFIG, clientId);
        config.put("security.protocol", "SASL_SSL");
        config.put("sasl.mechanism", "PLAIN");
        config.put("sasl.jaas.config", saslJaasConfig);

        SenderOptions<String, JsonNode> senderOptions = SenderOptions.create(config);
        return KafkaSender.create(senderOptions);
    }
}
