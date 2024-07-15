package com.example.spring6restmvc.controller;

import com.example.spring6restmvc.entity.Beer;
import com.example.spring6restmvc.model.BeerDTO;
import com.example.spring6restmvc.repository.BeerRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class BeerControllerIT {

    @Autowired
    private BeerController beerController;

    @Autowired
    private BeerRepository beerRepository;

    @Test
    void testById() {
        Beer beer = beerRepository.findAll().get(0);

        BeerDTO dto = beerController.getBeerById(beer.getId());

        assertThat(dto).isNotNull();
    }

    @Test
    void testByIdNotFound() {

        assertThrows(NotFoundException.class, () -> {
            beerController.getBeerById(UUID.randomUUID());
        });
    }

    @Test
    void testGetBeerList() {
        List<BeerDTO> dtos = beerController.listBears();

        assertThat(dtos.size()).isEqualTo(3);
    }

    @Rollback
    @Transactional
    @Test
    void testGetEmptyBeerList() {
        beerRepository.deleteAll();
        List<BeerDTO> dtos = beerController.listBears();

        assertThat(dtos.size()).isEqualTo(0);
    }
}