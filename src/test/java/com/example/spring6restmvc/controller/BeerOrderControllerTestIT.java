package com.example.spring6restmvc.controller;

import com.example.spring6restmvc.model.BeerOrderCreateDTO;
import com.example.spring6restmvc.model.BeerOrderLineCreateDTO;
import com.example.spring6restmvc.model.BeerOrderLineUpdateDTO;
import com.example.spring6restmvc.model.BeerOrderShipmentUpdateDTO;
import com.example.spring6restmvc.model.BeerOrderUpdateDTO;
import com.example.spring6restmvc.repository.BeerOrderRepository;
import com.example.spring6restmvc.repository.BeerRepository;
import com.example.spring6restmvc.repository.CustomerRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class BeerOrderControllerTestIT {

    @Autowired
    WebApplicationContext webApplicationContext;

    @Autowired
    BeerOrderRepository beerOrderRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    BeerRepository beerRepository;

    @Autowired
    ObjectMapper objectMapper;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();
    }

    @Test
    void testDeleteOrder() throws Exception {
        val beerOrder = beerOrderRepository.findAll().get(0);

        mockMvc.perform(delete(BeerOrderController.BEER_ORDER_PATH_ID, beerOrder.getId())
                                .with(BeerControllerTest.jwtRequestPostProcessors))
                .andExpect(status().isNoContent());

        assertTrue(beerOrderRepository.findById(beerOrder.getId()).isEmpty());

        mockMvc.perform(delete(BeerOrderController.BEER_ORDER_PATH_ID, beerOrder.getId())
                                .with(BeerControllerTest.jwtRequestPostProcessors))
                .andExpect(status().isNotFound());
    }

    @Transactional
    @Test
    void testUpdateBeerOrder() throws Exception {
        val beerOrder = beerOrderRepository.findAll().get(0);

        Set<BeerOrderLineUpdateDTO> lineUpdateDTOs = new HashSet<>();

        beerOrder.getBeerOrderLines().forEach(beerOrderLine -> {
            lineUpdateDTOs.add(BeerOrderLineUpdateDTO.builder()
                                       .id(beerOrderLine.getId())
                                       .beerId(beerOrderLine.getBeer().getId())
                                       .orderQuantity(beerOrderLine.getOrderQuantity())
                                       .quantityAllocated(beerOrderLine.getQuantityAllocated())
                                       .build());
        });

        val beerOrderUpdateDTO = BeerOrderUpdateDTO.builder()
                .customerId(beerOrder.getCustomer().getId())
                .customerRef("TestRef")
                .beerOrderLines(lineUpdateDTOs)
                .beerOrderShipment(BeerOrderShipmentUpdateDTO.builder()
                                           .tackingNumber("123456")
                                           .build())
                .build();

        mockMvc.perform(put(BeerOrderController.BEER_ORDER_PATH_ID, beerOrder.getId())
                                .contentType("application/json")
                                .content(objectMapper.writeValueAsString(beerOrderUpdateDTO))
                                .with(BeerControllerTest.jwtRequestPostProcessors))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerRef", is("TestRef")));
    }

    @Test
    void testCreateBeerOrder() throws Exception {
        val customer = customerRepository.findAll().get(0);
        val beer = beerRepository.findAll().get(0);

        val beerOrderCreateDTO = BeerOrderCreateDTO.builder()
                .customerId(customer.getId())
                .beerOrderLines(Set.of(BeerOrderLineCreateDTO.builder()
                                               .beerId(beer.getId())
                                               .orderQuantity(1)
                                               .build()))
                .build();

        mockMvc.perform(post(BeerOrderController.BEER_ORDER_PATH)
                                .contentType("application/json")
                                .content(objectMapper.writeValueAsString(beerOrderCreateDTO))
                                .with(BeerControllerTest.jwtRequestPostProcessors))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }

    @Test
    void testListBeerOrders() throws Exception {
        mockMvc.perform(get(BeerOrderController.BEER_ORDER_PATH)
                                .with(BeerControllerTest.jwtRequestPostProcessors))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.size()", greaterThan(0)));
    }

    @Test
    void testGetBeerOrderById() throws Exception {
        val beerOrder = beerOrderRepository.findAll().get(0);

        mockMvc.perform(get(BeerOrderController.BEER_ORDER_PATH_ID, beerOrder.getId())
                                .with(BeerControllerTest.jwtRequestPostProcessors))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(beerOrder.getId().toString())));
    }

}