package com.example.reactor.web.client.config;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Mono;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderRecord;

@SpringBootTest
class KafkaConfigTest {

    @Autowired
    private KafkaSender<String, String> kafkaSender;

    @Autowired
    private KafkaProperties props;


    @Test
    void kafkaReceiver() {
    }

    @Test
    void kafkaSender() {
        SenderRecord<String, String, String> message = SenderRecord.create(
                new ProducerRecord<>(props.getTopic1(), "key", "value"),
                "key"
        );

        kafkaSender.send(Mono.just(message))
                .doOnError(e -> System.err.println("Erro ao enviar: " + e.getMessage()))
                .subscribe(result -> System.out.println("Mensagem enviada com metadata: " + result.recordMetadata()));

    }
}