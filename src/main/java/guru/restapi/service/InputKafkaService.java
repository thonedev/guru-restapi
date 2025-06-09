package guru.restapi.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import guru.restapi.exception.NotFountException;
import guru.restapi.model.Beer;
import guru.restapi.model.BeerStyle;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.kafka.receiver.KafkaReceiver;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class InputKafkaService {
    private final KafkaReceiver<String, JsonNode> kafkaReceiver;
    private final ObjectMapper objectMapper;
    private final BeerService beerService;

    @PostConstruct
    public void startConsuming() {
        kafkaReceiver.receive()
                .doOnNext(record -> {
                    log.info("Recebido: {} ", record.value());
                    var json = record.value();
                    try {
                        var beer = Beer.builder()
                                .id(UUID.fromString(json.get("id").asText()))
                                .version(json.get("version").asInt())
                                .beerName(json.get("beerName").asText())
                                .beerStyle(BeerStyle.valueOf(json.get("beerStyle").asText()))
                                .upc(json.get("upc").asText())
                                .price(new BigDecimal(json.get("price").asText()))
                                .quantityOnHand(json.get("quantityOnHand").asInt())
                                .createdDate(LocalDateTime.now())
                                .updateDate(LocalDateTime.now())
                                .build();


                        switch (json.get("httpMethod").asText()) {
                            case "post" -> beerService.save(beer);
                            case "put" -> beerService.update(beer.getId(), beer);
                            case "patch" -> beerService.patch(beer.getId(), beer);
                            case "delete" -> beerService.delete(beer.getId());
                            default -> throw new Exception("método inválido");
                        }
                    } catch (JsonProcessingException e) {
                        log.error("Ocorreu um erro ao processar a mensagem. Erro: {}", e.getMessage(), e);
                    } catch (NotFountException e) {
                        log.error(e.getMessage(), e);
                    } catch (Exception e) {
                        log.error("Metodo http inválido");
                    }
                    record.receiverOffset().acknowledge();
                })
                .doOnError(err -> log.error("Ocorreu um erro ao processar a mensagem. Erro: {}", err.getMessage(), err))
                .subscribe();
    }
}
