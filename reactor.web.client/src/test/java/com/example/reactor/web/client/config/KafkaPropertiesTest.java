package com.example.reactor.web.client.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class KafkaPropertiesTest {

    @Autowired
    KafkaProperties kafkaProperties;

    @Test
    void testProperties() {
        System.out.println(kafkaProperties);
    }
}