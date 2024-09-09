package com.example.spring6restmvc.service;

import com.example.spring6restmvc.entity.BeerOrder;
import com.example.spring6restmvc.model.BeerOrderCreateDTO;
import com.example.spring6restmvc.model.BeerOrderDTO;
import com.example.spring6restmvc.model.BeerOrderUpdateDTO;

import org.springframework.data.domain.Page;

import java.util.Optional;
import java.util.UUID;

public interface BeerOrderService {

    Page<BeerOrderDTO> listBeerOrders(Integer pageNumber, Integer pageSize);

    Optional<BeerOrderDTO> getBeerOrderById(UUID beerOrderId);

    BeerOrder createOrder(BeerOrderCreateDTO beerOrderCreateDTO);

    BeerOrderDTO updateOrder(UUID beerOrderId, BeerOrderUpdateDTO beerOrderUpdateDTO);

    void deleteOrder(UUID beerOrderId);

//    BeerOrderDTO saveNewBeerOrder(BeerOrderDTO beerOrder);
//    Optional<BeerOrderDTO> updateBeerOrderById(UUID beerOrderId, BeerOrderDTO beerOrder);
//    Boolean deleteById(UUID beerOrderId);
//    Optional<BeerOrderDTO> patchBeerOrderById(UUID beerOrderId, BeerOrderDTO beerOrder);
}
