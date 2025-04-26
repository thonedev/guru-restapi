package com.example.reactor.web.client.controller;


import com.example.reactor.web.client.model.Beer;
import com.example.reactor.web.client.service.BeerServiceKafkaSender;
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
    public ResponseEntity<Void> create(@RequestBody Beer beer) {
        beer.setHttpMethod("post");
        beerService.send(beer);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> update(@PathVariable UUID id, @RequestBody Beer beer) {
        beer.setHttpMethod("put");
        beerService.send(beer);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("{id}")
    public ResponseEntity<Void> patch(@PathVariable UUID id, @RequestBody Beer beer) {
        beer.setHttpMethod("patch");
        beerService.send(beer);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        var beer = Beer.builder().httpMethod("delete").id(id).build();
        beerService.send(beer);
        return ResponseEntity.noContent().build();
    }
}
