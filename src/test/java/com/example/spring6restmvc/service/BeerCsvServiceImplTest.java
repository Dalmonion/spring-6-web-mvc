package com.example.spring6restmvc.service;

import com.example.spring6restmvc.model.BeerCSVRecord;
import org.junit.jupiter.api.Test;

import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class BeerCsvServiceImplTest {

    BeerCsvService beerCsvService = new BeerCsvServiceImpl();

    @Test
    void convertCSV() throws FileNotFoundException {
        File file = ResourceUtils.getFile("classpath:csvdata/beers.csv");

        List<BeerCSVRecord> recordList = beerCsvService.convertCSV(file);

        System.out.println(recordList.size());

        assertThat(recordList.size()).isGreaterThan(0);
    }
}