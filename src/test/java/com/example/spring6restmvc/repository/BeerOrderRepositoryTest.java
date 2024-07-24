package com.example.spring6restmvc.repository;

import com.example.spring6restmvc.entity.Beer;
import com.example.spring6restmvc.entity.BeerOrder;
import com.example.spring6restmvc.entity.BeerOrderShipment;
import com.example.spring6restmvc.entity.Customer;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BeerOrderRepositoryTest {

    @Autowired
    BeerOrderRepository beerOrderRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    BeerRepository beerRepository;

    Customer testCustomer;
    Beer testBeer;

    @BeforeEach
    void setUp() {
        testCustomer = customerRepository.findAll().get(0);
        testBeer = beerRepository.findAll().get(0);

    }

    @Test
    @Transactional
    void testBeerOrders() {
        BeerOrder beerOrder = BeerOrder.builder()
                .customerRef("test order")
                .customer(testCustomer)
                .beerOrderShipment(BeerOrderShipment.builder()
                                           .trackingNumber("1234").build())
                .build();

        BeerOrder savedBeerOrder = beerOrderRepository.save(beerOrder);

        System.out.println(savedBeerOrder.getCustomerRef());
    }
}