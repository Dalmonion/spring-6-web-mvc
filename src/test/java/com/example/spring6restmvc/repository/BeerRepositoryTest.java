package com.example.spring6restmvc.repository;

import com.example.spring6restmvc.entity.Beer;
import com.example.spring6restmvc.model.BeerStyle;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
class BeerRepositoryTest {

    @Autowired
    BeerRepository beerRepository;

    @Test
    void testSaveBeerNameTooLong() {

        assertThrows(ConstraintViolationException.class, () -> {
            Beer savedBeer = beerRepository.save(Beer.builder()
                                                         .beerName("My Beer 322094423094832903220944230948329032209442309483290322094423094832903220944230948329032209442309483290322094423094832903220944230948329032209442309483290")
                                                         .beerStyle(BeerStyle.PALE_ALE)
                                                         .upc("2131242")
                                                         .price(new BigDecimal("11.88"))
                                                         .build());

            beerRepository.flush();
        });
    }

    @Test
    void testSaveBeer() {
        Beer savedBeer = beerRepository.save(Beer.builder()
                                                     .beerName("My Beer")
                                                     .beerStyle(BeerStyle.PALE_ALE)
                                                     .upc("2131242")
                                                     .price(new BigDecimal("11.88"))
                                                     .build());

        beerRepository.flush();

        assertThat(savedBeer).isNotNull();
        assertThat(savedBeer.getId()).isNotNull();
    }
}