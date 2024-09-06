package com.example.spring6restmvc.mapper;

import com.example.spring6restmvc.entity.BeerOrder;
import com.example.spring6restmvc.entity.BeerOrderLine;
import com.example.spring6restmvc.entity.BeerOrderShipment;
import com.example.spring6restmvc.model.BeerOrderDTO;
import com.example.spring6restmvc.model.BeerOrderLineDTO;
import com.example.spring6restmvc.model.BeerOrderShipmentDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface BeerOrderMapper {
    BeerOrder toBeerOrder(BeerOrderDTO dto);

    BeerOrderDTO toBeerOrderDTO(BeerOrder beerOrder);

    @Mapping(target = "beerOrder", ignore = true)
    BeerOrderLine toBeerOrderLine(BeerOrderLineDTO dto);

    BeerOrderLineDTO toBeerOrderLineDDTO(BeerOrderLine beerOrderLine);

    @Mapping(target = "beerOrder", ignore = true)
    BeerOrderShipment toBeerOrderShipment(BeerOrderShipmentDTO dto);

    BeerOrderShipmentDTO toBeerOrderShipmentDTO(BeerOrderShipment beerOrderShipment);
}
