package com.example.spring6restmvc.config;

import com.example.spring6restmvc.entity.Beer;
import com.example.spring6restmvc.entity.Customer;
import com.example.spring6restmvc.model.BeerCSVRecord;
import com.example.spring6restmvc.model.BeerStyle;
import com.example.spring6restmvc.repository.BeerRepository;
import com.example.spring6restmvc.repository.CustomerRepository;
import com.example.spring6restmvc.service.BeerCsvService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class BootStrapData implements CommandLineRunner {

    private final BeerRepository beerRepository;
    private final CustomerRepository customerRepository;
    private final BeerCsvService beerCsvService;

    @Transactional
    @Override
    public void run(String... args) throws Exception {
        loadBeerData();
        loadCustomerData();
        loadCsvData();
    }

    private void loadCsvData() throws FileNotFoundException {
        if (beerRepository.count() < 10) {
            File file = ResourceUtils.getFile("classpath:csvdata/beers.csv");

            List<BeerCSVRecord> records = beerCsvService.convertCSV(file);

            records.forEach(beerCSVRecord -> {
                BeerStyle beerStyle = switch (beerCSVRecord.getStyle()) {
                    case "American Pale Lager" -> BeerStyle.LAGER;
                    case "American Pale Ale (APA)", "American Black Ale", "Belgian Dark Ale", "American Blonde Ale" ->
                            BeerStyle.ALE;
                    case "American IPA", "American Double / Imperial IPA", "Belgian IPA" -> BeerStyle.IPA;
                    case "American Porter" -> BeerStyle.PORTER;
                    case "Oatmeal Stout", "American Stout" -> BeerStyle.STOUT;
                    case "Saison / Farmhouse Ale" -> BeerStyle.SAISON;
                    case "Fruit / Vegetable Beer", "Winter Warmer", "Berliner Weissbier" -> BeerStyle.WHEAT;
                    case "English Pale Ale" -> BeerStyle.PALE_ALE;
                    default -> BeerStyle.PILSNER;
                };

                beerRepository.save(Beer.builder()
                                            .beerName(StringUtils.abbreviate(beerCSVRecord.getBeer(), 50))
                                            .beerStyle(beerStyle)
                                            .price(BigDecimal.TEN)
                                            .upc(beerCSVRecord.getRow().toString())
                                            .quantityOnHand(beerCSVRecord.getCount())
                                            .build());
            });
        }
    }

    private void loadBeerData() {
        if (beerRepository.count() == 0) {
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

            beerRepository.saveAll(List.of(beer1, beer2, beer3));
        }
    }

    private void loadCustomerData() {
        if (customerRepository.count() == 0) {
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

            customerRepository.saveAll(List.of(customer1, customer2, customer3));
        }
    }
}
