package com.example.spring6restmvc.config;

import com.example.spring6restmvc.entity.Beer;
import com.example.spring6restmvc.entity.Customer;
import com.example.spring6restmvc.model.BeerStyle;
import com.example.spring6restmvc.repository.BeerRepository;
import com.example.spring6restmvc.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class BootStrapData implements CommandLineRunner {

    private final BeerRepository beerRepository;
    private final CustomerRepository customerRepository;

    @Override
    public void run(String... args) throws Exception {
        Beer beer1 = Beer.builder()
                .beerName("Test 1")
                .beerStyle(BeerStyle.PALE_ALE)
                .upc("12345")
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .quantityOnHand(123)
                .price(BigDecimal.valueOf(12.99))
                .build();

        Beer beer2 = Beer.builder()
                .beerName("Test 2")
                .beerStyle(BeerStyle.LAGER)
                .upc("67890")
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .quantityOnHand(456)
                .price(BigDecimal.valueOf(22.19))
                .build();

        Beer beer3 = Beer.builder()
                .beerName("Test 3")
                .beerStyle(BeerStyle.GOSE)
                .upc("123098")
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .quantityOnHand(999)
                .price(BigDecimal.valueOf(9.99))
                .build();

        Customer customer1 = Customer.builder()
                .customerName("test customer 1")
                .createdDate(LocalDateTime.now())
                .modifiedDate(LocalDateTime.now())
                .build();

        Customer customer2 = Customer.builder()
                .customerName("test customer 2")
                .createdDate(LocalDateTime.now())
                .modifiedDate(LocalDateTime.now())
                .build();

        Customer customer3 = Customer.builder()
                .customerName("test customer 3")
                .createdDate(LocalDateTime.now())
                .modifiedDate(LocalDateTime.now())
                .build();

        beerRepository.saveAll(List.of(beer1, beer2, beer3));
        customerRepository.saveAll(List.of(customer1, customer2, customer3));
    }
}
