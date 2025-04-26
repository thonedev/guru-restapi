package guru.restapi.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import guru.restapi.model.Beer;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.kafka.receiver.KafkaReceiver;

@Slf4j
@Service
@RequiredArgsConstructor
public class InputKafkaService {
    private final KafkaReceiver<String, String> kafkaReceiver;
    private final ObjectMapper objectMapper;
    private final BeerService beerService;

    @PostConstruct
    public void startConsuming() {
        kafkaReceiver.receive()
                .doOnNext(record -> {
                    log.info("Recebido: {} ", record.value());
                    try {
                        var beerDto = objectMapper.readValue(record.value(), Beer.class);
                        switch (beerDto.getHttpMethod()){
                            case "post" -> beerService.save(beerDto);
                            case "put" -> beerService.update(beerDto.getId(), beerDto);
                            case "patch" -> beerService.patch(beerDto.getId(), beerDto);
                            case "delete" -> beerService.delete(beerDto.getId());
                            default -> throw new Exception("método inválido");
                        }
                    } catch (JsonProcessingException e) {
                        log.error("Ocorreu um erro ao processar a mensagem. Erro: {}", e.getMessage(), e);
                    } catch (Exception e) {
                        log.error("Metodo http inválido");
                    }
                    record.receiverOffset().acknowledge();
                })
                .doOnError(err -> log.error("Ocorreu um erro ao processar a mensagem. Erro: {}", err.getMessage(), err))
                .subscribe();
    }
}
