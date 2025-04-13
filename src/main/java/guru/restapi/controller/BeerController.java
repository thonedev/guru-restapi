package guru.restapi.controller;

import guru.restapi.model.Beer;
import guru.restapi.service.BeerService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/beer")
@AllArgsConstructor
public class BeerController {

    private final BeerService beerService;

    @PostMapping
    public ResponseEntity<Beer> create(@RequestBody Beer beer){
        var createdBeer = beerService.save(beer);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(createdBeer.getId())
                .toUri();
        return ResponseEntity.created(location).body(createdBeer);
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
    public ResponseEntity<Beer> update(@PathVariable UUID id, @RequestBody Beer beer){
        return ResponseEntity.ok(beerService.update(id, beer));
    }

    @PatchMapping("{id}")
    public ResponseEntity<Beer> patch(@PathVariable UUID id, @RequestBody Beer beer){
        return ResponseEntity.ok(beerService.update(id, beer));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        beerService.delete(id);
        return ResponseEntity.noContent().build();
    }


}
