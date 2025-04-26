package com.example.reactor.web.client.service;

import com.example.reactor.web.client.config.KafkaProperties;
import com.example.reactor.web.client.model.Beer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderRecord;

@Service
public class BeerServiceKafkaSender {

    @Autowired
    private KafkaSender<String, String> kafkaSender;

    @Autowired
    private KafkaProperties props;

    @Autowired
    private ObjectMapper objectMapper;

    public void send(Beer beer) {
        String value;
        try {
            value = objectMapper.writeValueAsString(beer);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        SenderRecord<String, String, String> message = SenderRecord.create(
                new ProducerRecord<>(props.getTopic1(), "", value),
                ""
        );

        kafkaSender.send(Mono.just(message))
                .doOnError(e -> System.err.println("Erro ao enviar: " + e.getMessage()))
                .subscribe(result -> System.out.println("Mensagem enviada com metadata: " + result.recordMetadata()));
    }
}
