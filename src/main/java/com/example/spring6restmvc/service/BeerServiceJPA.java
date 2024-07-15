package com.example.spring6restmvc.service;

import com.example.spring6restmvc.mapper.BeerMapper;
import com.example.spring6restmvc.model.BeerDTO;
import com.example.spring6restmvc.repository.BeerRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

@Service
@Primary
@RequiredArgsConstructor
public class BeerServiceJPA implements BeerService {

    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;

    @Override
    public List<BeerDTO> listBeers() {
        return beerRepository.findAll().stream()
                .map(beerMapper::beerToBeerDto)
                .toList();
    }

    @Override
    public Optional<BeerDTO> getBeerById(UUID id) {
        return Optional.ofNullable(beerMapper.beerToBeerDto(beerRepository.findById(id)
                                                                    .orElse(null)));
    }

    @Override
    public BeerDTO saveNewBeer(BeerDTO beer) {
        return beerMapper.beerToBeerDto(beerRepository.save(beerMapper.beerDtoToBeer(beer)));
    }

    @Override
    public Optional<BeerDTO> updateBeerById(UUID beerId, BeerDTO beer) {
        AtomicReference<Optional<BeerDTO>> atomicReference = new AtomicReference<>();

        beerRepository.findById(beerId).ifPresentOrElse(foundedBeer -> {
            foundedBeer.setBeerName(beer.getBeerName());
            foundedBeer.setBeerStyle(beer.getBeerStyle());
            foundedBeer.setPrice(beer.getPrice());
            foundedBeer.setUpc(beer.getUpc());
            foundedBeer.setQuantityOnHand(beer.getQuantityOnHand());

            atomicReference.set(Optional.of(beerMapper
                                                    .beerToBeerDto(beerRepository.save(foundedBeer))));
        }, () -> {
            atomicReference.set(Optional.empty());
        });
        return atomicReference.get();
    }

    @Override
    public Boolean deleteById(UUID beerId) {
        if (beerRepository.existsById(beerId)) {
            beerRepository.deleteById(beerId);
            return true;
        }
        return false;
    }

    @Override
    public Optional<BeerDTO> patchBeerById(UUID beerId, BeerDTO beer) {
        AtomicReference<Optional<BeerDTO>> atomicReference = new AtomicReference<>();

        beerRepository.findById(beerId).ifPresentOrElse(foundedBeer -> {
            if (beer.getBeerName() != null) {
                foundedBeer.setBeerName(beer.getBeerName());
            }
            if (beer.getBeerStyle() != null) {
                foundedBeer.setBeerStyle(beer.getBeerStyle());
            }
            if (beer.getPrice() != null) {
                foundedBeer.setPrice(beer.getPrice());
            }
            if (beer.getUpc() != null) {
                foundedBeer.setUpc(beer.getUpc());
            }
            if (beer.getQuantityOnHand() != null) {
                foundedBeer.setQuantityOnHand(beer.getQuantityOnHand());
            }

            atomicReference.set(Optional.of(beerMapper
                                                    .beerToBeerDto(beerRepository.save(foundedBeer))));
        }, () -> {
            atomicReference.set(Optional.empty());
        });
        return atomicReference.get();
    }
}
