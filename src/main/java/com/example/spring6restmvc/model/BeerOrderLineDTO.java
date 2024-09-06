package com.example.spring6restmvc.model;

import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;
import java.util.UUID;

@Builder
@Data
public class BeerOrderLineDTO {

    private UUID id;
    private Long version;
    private Timestamp createdDate;
    private Timestamp lastModifiedDate;
    private BeerOrderDTO beerOrder;
    private BeerDTO beer;
    private Integer orderQuantity = 0;
    private Integer quantityAllocated = 0;
}
