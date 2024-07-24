package com.example.spring6restmvc.repository;

import com.example.spring6restmvc.entity.BeerOrder;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BeerOrderRepository extends JpaRepository<BeerOrder, UUID> {
}
