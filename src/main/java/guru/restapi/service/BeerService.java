package guru.restapi.service;

import guru.restapi.model.Beer;

import java.util.List;
import java.util.UUID;

public interface BeerService {
    Beer save(Beer beer);
    Beer update(UUID beerId, Beer beer);
    Beer patch(UUID beerId, Beer beer);
    void delete(UUID beerId);
    List<Beer> findAll();
    Beer findById(UUID beerId);
}
