package com.example.reactor.web.client;

import com.example.reactor.web.client.model.Beer;
import com.example.reactor.web.client.model.BeerStyle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@RestController
public class controllerWebClient {

    private final WebClient webClient;

    public controllerWebClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder
                .baseUrl("http://localhost:8080/api/v1")
                .build();
    }

    @RequestMapping(value = "call", method = RequestMethod.GET)
    public ResponseEntity fetchBeers(){
        var response = webClient
                .get()
                .uri("/beer")
                .retrieve()
                .bodyToFlux(Beer.class)
                .doOnError(e -> log.info("something"))
                ;
        return ResponseEntity.ok("ok");
    }

    @RequestMapping(value = "post", method = RequestMethod.POST)
    public ResponseEntity postBeer(){
        var response = webClient
                .post()
                .uri("/beer")
                .bodyValue(Beer.builder()
                        .id(UUID.randomUUID())
                        .version(1)
                        .beerName("Create")
                        .beerStyle(BeerStyle.PALE_ALE)
                        .upc("12356222")
                        .price(new BigDecimal("11.99"))
                        .quantityOnHand(392)
                        .createdDate(LocalDateTime.now())
                        .updateDate(LocalDateTime.now())
                        .build())
                .exchangeToMono(c -> {
                    if(c.statusCode().is2xxSuccessful()){
                        return Mono.just("tudo certo! " + c.statusCode().value());
                    }else{
                        return Mono.just("something went wrong! " + c.statusCode().value());
                    }
                })
                .doOnNext(log::info);
        return ResponseEntity.ok(response);
    }
}
