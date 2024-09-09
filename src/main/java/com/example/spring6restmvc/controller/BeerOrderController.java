package com.example.spring6restmvc.controller;

import com.example.spring6restmvc.entity.BeerOrder;
import com.example.spring6restmvc.model.BeerOrderCreateDTO;
import com.example.spring6restmvc.model.BeerOrderDTO;
import com.example.spring6restmvc.model.BeerOrderUpdateDTO;
import com.example.spring6restmvc.service.BeerOrderService;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
public class BeerOrderController {

    public static final String BEER_ORDER_PATH = "/api/v1/beerorder";
    public static final String BEER_ORDER_PATH_ID = BEER_ORDER_PATH + "/{beerOrderId}";

    private final BeerOrderService beerOrderService;

    @PutMapping(BEER_ORDER_PATH_ID)
    public ResponseEntity<BeerOrderDTO> updateOrder(@PathVariable UUID beerOrderId,
                                                    @RequestBody BeerOrderUpdateDTO beerOrderUpdateDTO) {
        return ResponseEntity.ok(beerOrderService.updateOrder(beerOrderId, beerOrderUpdateDTO));
    }

    @PostMapping(BEER_ORDER_PATH)
    public ResponseEntity<Void> createOrder(@RequestBody BeerOrderCreateDTO beerOrderCreateDTO) {
        BeerOrder savedOrder = beerOrderService.createOrder(beerOrderCreateDTO);

        return ResponseEntity.created(URI.create(BEER_ORDER_PATH + "/" + savedOrder.getId().toString())).build();
    }

    @GetMapping(BEER_ORDER_PATH_ID)
    public BeerOrderDTO getBeerOrderById(@PathVariable UUID beerOrderId) {
        return beerOrderService.getBeerOrderById(beerOrderId).orElseThrow(NotFoundException::new);
    }

    @GetMapping(BEER_ORDER_PATH)
    public Page<BeerOrderDTO> listBeerOrders(@RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                                             @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        return beerOrderService.listBeerOrders(pageNumber, pageSize);
    }
}
