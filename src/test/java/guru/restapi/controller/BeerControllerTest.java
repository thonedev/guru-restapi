package guru.restapi.controller;

import guru.restapi.model.Beer;
import guru.restapi.model.BeerStyle;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;

@SpringBootTest
@AutoConfigureWebTestClient
class BeerControllerTest {

    @Autowired
    WebTestClient webTestClient;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void testBeerLifecycle() {
        Beer beer = Beer.builder()
                .beerName("New Beer")
                .beerStyle(BeerStyle.IPA)
                .upc("123456789")
                .price(new BigDecimal("9.99"))
                .quantityOnHand(100)
                .build();

        // Test Create
        var location = webTestClient.post()
                .uri("/api/v1/beer")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(beer)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().exists("Location")
                .returnResult(Void.class)
                .getResponseHeaders()
                .getFirst("Location");

        // Test Delete
        assert location != null;
        webTestClient.delete()
                .uri(location)
                .exchange()
                .expectStatus().isNoContent();
    }
}