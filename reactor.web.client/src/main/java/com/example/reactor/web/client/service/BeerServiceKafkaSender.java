package com.example.reactor.web.client.service;

import com.example.reactor.web.client.config.KafkaProperties;
import com.example.reactor.web.client.model.Beer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderRecord;

@Slf4j
@Service
public class BeerServiceKafkaSender {

    @Autowired
    private KafkaSender<String, JsonNode> kafkaSender;

    @Autowired
    private KafkaProperties props;

    @Autowired
    private ObjectMapper objectMapper;

    public void send(JsonNode value) {

        SenderRecord<String, JsonNode, String> message = SenderRecord.create(
                new ProducerRecord<>(props.getTopic1(), value),
                ""
        );
        for(int i=0; i<1; i++)
            kafkaSender.send(Mono.just(message))
                .doOnError(e -> System.err.println("Erro ao enviar: " + e.getMessage()))
                .subscribe(result -> log.info(
                                "\n-----------------------------\n" +
                                "Mensagem enviada com com sucesso:\n" +
                                "Topic: {}\n" +
                                "Partition: {}\n" +
                                "Offset: {}\n" +
                                "Timestamp: {}\n" +
                                "-----------------------------",
                        result.recordMetadata().topic(), result.recordMetadata().partition(), result.recordMetadata().offset(), result.recordMetadata().timestamp()));
    }
}
