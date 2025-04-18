package guru.restapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import guru.restapi.model.Beer;
import guru.restapi.model.BeerStyle;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class BeerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void testBeerLifecycle() throws Exception {
        Beer beer = Beer.builder()
                .beerName("New Beer")
                .beerStyle(BeerStyle.IPA)
                .upc("123456789")
                .price(new BigDecimal("9.99"))
                .quantityOnHand(100)
                .build();

        // Test Create
        String location = mockMvc.perform(post("/api/v1/beer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(beer)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andReturn().getResponse().getHeader("Location");

        // Test Delete
        mockMvc.perform(delete(location))
                .andExpect(status().isNoContent());
    }
}