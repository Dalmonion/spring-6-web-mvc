package com.example.spring6restmvc.repository;

import com.example.spring6restmvc.entity.Customer;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {
}
