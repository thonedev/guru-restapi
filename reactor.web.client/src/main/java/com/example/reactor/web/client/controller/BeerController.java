package com.example.reactor.web.client.controller;


import com.example.reactor.web.client.service.BeerServiceKafkaSender;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/kafka/beer")
@AllArgsConstructor
public class BeerController {

    private final BeerServiceKafkaSender beerService;

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody ObjectNode beer) {
        beer.put("httpMethod", "post");
        beerService.send(beer);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> update(@PathVariable UUID id, @RequestBody ObjectNode beer) {
        beer.put("httpMethod","put");
        beerService.send(beer);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("{id}")
    public ResponseEntity<Void> patch(@PathVariable UUID id, @RequestBody ObjectNode beer) {
        beer.put("httpMethod","patch");
        beerService.send(beer);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        ObjectNode beer = new ObjectMapper().createObjectNode();
        beer.put("httpMethod", "delete");
        beer.put("id", id.toString());
        beerService.send(beer);
        return ResponseEntity.noContent().build();
    }
}
