package com.example.spring6restmvc.repository;

import com.example.spring6restmvc.entity.Beer;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BeerRepository extends JpaRepository<Beer, UUID> {
}
