package com.example.spring6restmvc.mapper;

import com.example.spring6restmvc.entity.Beer;
import com.example.spring6restmvc.model.BeerDTO;
import org.mapstruct.Mapper;

@Mapper
public interface BeerMapper {

    Beer beerDtoToBeer(BeerDTO dto);
    BeerDTO beerToBeerDto(Beer beer);
}
