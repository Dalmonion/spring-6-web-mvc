package com.example.spring6restmvc.controller;

import com.example.spring6restmvc.entity.Beer;
import com.example.spring6restmvc.mapper.BeerMapper;
import com.example.spring6restmvc.model.BeerDTO;
import com.example.spring6restmvc.model.BeerStyle;
import com.example.spring6restmvc.repository.BeerRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @Autowired
    private BeerMapper beerMapper;

    @Test
    void testDeleteNotFound() {
        assertThrows(NotFoundException.class, () -> {
            beerController.deleteById(UUID.randomUUID());
        });
    }

    @Rollback
    @Transactional
    @Test
    void testDeleteBeerById() {
        Beer beer = beerRepository.findAll().get(0);

        ResponseEntity responseEntity = beerController.deleteById(beer.getId());

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.valueOf(204));
        assertThat(beerRepository.findById(beer.getId())).isEmpty();
    }

    @Test
    void testUpdateNotFound() {
        assertThrows(NotFoundException.class, () -> {
            beerController.updateBydId(UUID.randomUUID(), BeerDTO.builder().build());
        });
    }

    @Rollback
    @Transactional
    @Test
    void testUpdateBeer() {
        Beer beer = beerRepository.findAll().get(0);
        BeerDTO beerDTO = beerMapper.beerToBeerDto(beer);
        beerDTO.setId(null);
        beerDTO.setVersion(null);
        final String beerName = "Updated";
        beerDTO.setBeerName(beerName);

        ResponseEntity responseEntity = beerController.updateBydId(beer.getId(), beerDTO);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.valueOf(204));

        Beer updatedBeer = beerRepository.findById(beer.getId()).get();
        assertThat(updatedBeer.getBeerName()).isEqualTo(beerName);
    }

    @Rollback
    @Transactional
    @Test
    void testCreateBeer() {
        BeerDTO toCreate = BeerDTO.builder()
                .beerName("new beer")
                .beerStyle(BeerStyle.IPA)
                .build();

        ResponseEntity responseEntity = beerController.handlePost(toCreate);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.valueOf(201));
        assertThat(responseEntity.getHeaders().getLocation()).isNotNull();

        String[] locationUUID = responseEntity.getHeaders().getLocation().getPath().split("/");
        UUID savedUUID = UUID.fromString(locationUUID[3]);

        Beer beer = beerRepository.findById(savedUUID).get();
        assertThat(beer).isNotNull();
    }

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