package guru.restapi.service;

import guru.restapi.model.Beer;
import guru.restapi.model.BeerStyle;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class BeerServiceImpl implements BeerService {

    private final static Map<UUID, Beer> beerMap = new HashMap<>();

    public BeerServiceImpl() {

        Beer beer1 = Beer.builder()
                .id(UUID.randomUUID())
                .version(1)
                .beerName("Galaxy Cat")
                .beerStyle(BeerStyle.PALE_ALE)
                .upc("12356")
                .price(new BigDecimal("12.99"))
                .quantityOnHand(122)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        Beer beer2 = Beer.builder()
                .id(UUID.randomUUID())
                .version(1)
                .beerName("Crank")
                .beerStyle(BeerStyle.PALE_ALE)
                .upc("12356222")
                .price(new BigDecimal("11.99"))
                .quantityOnHand(392)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        Beer beer3 = Beer.builder()
                .id(UUID.randomUUID())
                .version(1)
                .beerName("Sunshine City")
                .beerStyle(BeerStyle.IPA)
                .upc("12356")
                .price(new BigDecimal("13.99"))
                .quantityOnHand(144)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        beerMap.put(beer1.getId(), beer1);
        beerMap.put(beer2.getId(), beer2);
        beerMap.put(beer3.getId(), beer3);

    }

    @Override
    public Beer save(Beer beer) {

        Beer beerToSave = Beer.builder()
                .id(UUID.randomUUID())
                .version(beer.getVersion())
                .beerName(beer.getBeerName())
                .beerStyle(beer.getBeerStyle())
                .upc(beer.getUpc())
                .price(beer.getPrice())
                .quantityOnHand(beer.getQuantityOnHand())
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();


        beerMap.put(beerToSave.getId(), beerToSave);

        return beerToSave;
    }

    @Override
    public Beer update(UUID beerId, Beer beer) {
        var beerToUpdate = beerMap.get(beerId);

        beerToUpdate.setBeerName(beer.getBeerName());
        beerToUpdate.setBeerStyle(beer.getBeerStyle());
        beerToUpdate.setUpdateDate(LocalDateTime.now());
        beerToUpdate.setPrice(beer.getPrice());
        beerToUpdate.setUpc(beer.getUpc());
        beerToUpdate.setVersion(beer.getVersion());
        beerToUpdate.setQuantityOnHand(beer.getQuantityOnHand());

        return beerToUpdate;
    }

    @Override
    public Beer patch(UUID beerId, Beer beer) {
        var beerToUpdate = beerMap.get(beerId);

        if (StringUtils.hasText(beer.getBeerName())) {
            beerToUpdate.setBeerName(beer.getBeerName());
        }

        if (beer.getBeerStyle() != null) {
            beerToUpdate.setBeerStyle(beer.getBeerStyle());
        }

        if (beer.getPrice() != null) {
            beerToUpdate.setPrice(beer.getPrice());
        }

        if (beer.getVersion() != null) {
            beerToUpdate.setVersion(beer.getVersion());
        }

        if (StringUtils.hasText(beer.getUpc())) {
            beerToUpdate.setUpc(beer.getUpc());
        }

        if (beer.getQuantityOnHand() != null) {
            beerToUpdate.setQuantityOnHand(beer.getQuantityOnHand());
        }

        beerToUpdate.setUpdateDate(LocalDateTime.now());

        return beerToUpdate;
    }

    @Override
    public void delete(UUID beerId) {
        beerMap.remove(beerId);
    }

    @Override
    public List<Beer> findAll() {
        return new ArrayList<>(beerMap.values());
    }

    @Override
    public Beer findById(UUID beerId) {
        return beerMap.get(beerId);
    }
}
