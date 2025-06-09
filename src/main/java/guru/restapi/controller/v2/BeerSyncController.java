package guru.restapi.controller.v2;

import com.fasterxml.jackson.databind.JsonNode;
import guru.restapi.model.Beer;
import guru.restapi.model.BeerStyle;
import guru.restapi.service.BeerService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v2/beer")
@AllArgsConstructor
public class BeerSyncController {

    private final BeerService beerService;

    @PostMapping
    public Mono<ResponseEntity<Beer>> create(@RequestBody JsonNode json, @RequestHeader("Host") String host) {

        var beer = Beer.builder()
                .id(UUID.fromString(json.get("id").asText()))
                .version(json.get("version").asInt())
                .beerName(json.get("beerName").asText())
                .beerStyle(BeerStyle.valueOf(json.get("beerStyle").asText()))
                .upc(json.get("upc").asText())
                .price(new BigDecimal(json.get("price").asText()))
                .quantityOnHand(json.get("quantityOnHand").asInt())
                .createdDate(LocalDateTime.now())
                .build();


        return beerService.save(beer)
                .map(createdBeer -> {
                    String baseUrl = "http://" + host;
                    URI location = UriComponentsBuilder.fromUriString(baseUrl)
                            .path("/api/v2/beer/{id}")
                            .buildAndExpand(createdBeer.getId())
                            .toUri();
                    return ResponseEntity.created(location).body(createdBeer);
                });
    }

    @GetMapping
    public ResponseEntity<List<Beer>> findAll(){
        return ResponseEntity.ok(beerService.findAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<Beer> findById(@PathVariable UUID id){
        return ResponseEntity.ok(beerService.findById(id));
    }

    @PutMapping("{id}")
    public ResponseEntity<Beer> update(@PathVariable UUID id, @RequestBody JsonNode json){
        var beer = Beer.builder()
                .id(UUID.fromString(json.get("id").asText()))
                .version(json.get("version").asInt())
                .beerName(json.get("name").asText())
                .beerStyle(BeerStyle.valueOf(json.get("beerStyle").asText()))
                .upc(json.get("upc").asText())
                .price(new BigDecimal(json.get("price").asText()))
                .quantityOnHand(json.get("quantityOnHand").asInt())
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        return ResponseEntity.ok(beerService.update(id, beer));
    }

    @PatchMapping("{id}")
    public ResponseEntity<Beer> patch(@PathVariable UUID id, @RequestBody JsonNode json){
        var beer = Beer.builder()
                .id(UUID.fromString(json.get("id").asText()))
                .version(json.get("version").asInt())
                .beerName(json.get("name").asText())
                .beerStyle(BeerStyle.valueOf(json.get("beerStyle").asText()))
                .upc(json.get("upc").asText())
                .price(new BigDecimal(json.get("price").asText()))
                .quantityOnHand(json.get("quantityOnHand").asInt())
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        return ResponseEntity.ok(beerService.update(id, beer));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        beerService.delete(id);
        return ResponseEntity.noContent().build();
    }


}
