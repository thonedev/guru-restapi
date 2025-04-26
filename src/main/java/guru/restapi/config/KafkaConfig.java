package guru.restapi.config;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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

    @Bean
    public KafkaReceiver<String, String> kafkaReceiver(KafkaProperties props) {
        Map<String, Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, props.getBootstrapServers());
        config.put(ConsumerConfig.GROUP_ID_CONFIG, props.getConsumer().getGroupId());
        config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, props.getConsumer().getAutoOffsetReset());
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, props.getConsumer().getKeyDeserializer());
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, props.getConsumer().getValueDeserializer());

        ReceiverOptions<String, String> options = ReceiverOptions.<String, String>create(config)
                .subscription(Collections.singleton(props.getTopic1())); // ou topic2 etc.

        return KafkaReceiver.create(options);
    }

    @Bean
    public KafkaSender<String, String> kafkaSender(KafkaProperties props) {
        Map<String, Object> config = new HashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, props.getBootstrapServers());
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, props.getProducer().getKeySerializer());
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, props.getProducer().getValueSerializer());

        SenderOptions<String, String> senderOptions = SenderOptions.create(config);
        return KafkaSender.create(senderOptions);
    }
}
