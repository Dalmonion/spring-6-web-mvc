package com.example.spring6restmvc.service;

import com.example.spring6restmvc.controller.NotFoundException;
import com.example.spring6restmvc.entity.BeerOrder;
import com.example.spring6restmvc.entity.BeerOrderLine;
import com.example.spring6restmvc.entity.Customer;
import com.example.spring6restmvc.mapper.BeerOrderMapper;
import com.example.spring6restmvc.model.BeerOrderCreateDTO;
import com.example.spring6restmvc.model.BeerOrderDTO;
import com.example.spring6restmvc.repository.BeerOrderRepository;
import com.example.spring6restmvc.repository.BeerRepository;
import com.example.spring6restmvc.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BeerOrderServiceJPA implements BeerOrderService {

    private final static int DEFAULT_PAGE = 0;
    private final static int DEFAULT_PAGE_SIZE = 25;

    private final BeerOrderRepository beerOrderRepository;
    private final BeerOrderMapper beerOrderMapper;
    private final CustomerRepository customerRepository;
    private final BeerRepository beerRepository;

    @Override
    public Page<BeerOrderDTO> listBeerOrders(Integer pageNumber, Integer pageSize) {
        int queryPageNumber = DEFAULT_PAGE;
        int queryPageSize = DEFAULT_PAGE_SIZE;

        if (pageNumber != null && pageNumber > 0) {
            queryPageNumber = pageNumber + 1;
        }

        if (pageSize != null) {
            if (pageSize > 1000) {
                queryPageSize = 1000;

            } else {
                queryPageSize = pageSize;
            }
        }

        PageRequest pageRequest = PageRequest.of(queryPageNumber, queryPageSize);
        Page<BeerOrder> beerOrderPage = beerOrderRepository.findAll(pageRequest);

        return beerOrderPage.map(beerOrderMapper::toBeerOrderDTO);
    }

    @Override
    public Optional<BeerOrderDTO> getBeerOrderById(UUID beerOrderId) {
        return Optional.ofNullable(beerOrderMapper.toBeerOrderDTO(beerOrderRepository.findById(beerOrderId)
                                                                          .orElse(null)));
    }

    @Override
    public BeerOrder createOrder(BeerOrderCreateDTO beerOrderCreateDTO) {
        Customer customer = customerRepository.findById(beerOrderCreateDTO.getCustomerId())
                .orElseThrow(NotFoundException::new);

        Set<BeerOrderLine> beerOrderLines = new HashSet<>();

        beerOrderCreateDTO.getBeerOrderLines().forEach(beerOrderLine -> {
            beerOrderLines.add(BeerOrderLine.builder()
                                       .beer(beerRepository.findById(beerOrderLine.getBeerId()).orElseThrow(NotFoundException::new))
                                       .orderQuantity(beerOrderLine.getOrderQuantity())
                                       .build());
        });

        return beerOrderRepository.save(BeerOrder.builder()
                                                .customer(customer)
                                                .beerOrderLines(beerOrderLines)
                                                .customerRef(beerOrderCreateDTO.getCustomerRef())
                                                .build());
    }
}
