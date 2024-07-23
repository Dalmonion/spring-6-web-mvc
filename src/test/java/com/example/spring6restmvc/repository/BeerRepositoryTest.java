package com.example.spring6restmvc.repository;

import com.example.spring6restmvc.config.BootStrapData;
import com.example.spring6restmvc.entity.Beer;
import com.example.spring6restmvc.model.BeerStyle;
import com.example.spring6restmvc.service.BeerCsvServiceImpl;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@Import({BootStrapData.class, BeerCsvServiceImpl.class})
class BeerRepositoryTest {

    @Autowired
    BeerRepository beerRepository;

    @Test
    void testGerBeerListByBeerStyle() {
        Page<Beer> beerPage = beerRepository.findAllByBeerStyle(BeerStyle.IPA, null);

        assertThat(beerPage.getContent().size()).isEqualTo(547);
    }

    @Test
    void testGetBeerListByName() {
        Page<Beer> list = beerRepository.findAllByBeerNameIsLikeIgnoreCase("%IPA%", null);

        assertThat(list.getContent().size()).isEqualTo(336);
    }

    @Test
    void testSaveBeerNameTooLong() {

        assertThrows(ConstraintViolationException.class, () -> {
            Beer savedBeer = beerRepository.save(Beer.builder()
                                                         .beerName(
                                                                 "My Beer 322094423094832903220944230948329032209442309483290322094423094832903220944230948329032209442309483290322094423094832903220944230948329032209442309483290")
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