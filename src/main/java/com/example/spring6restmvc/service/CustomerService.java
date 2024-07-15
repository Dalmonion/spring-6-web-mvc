package com.example.spring6restmvc.service;

import com.example.spring6restmvc.model.Customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerService {
    List<Customer> listCustomers();
    Optional<Customer> getCustomerById(UUID customerId);

    Customer createCustomer(Customer customer);

    void updateById(UUID customerId, Customer customer);

    void deleteBydId(UUID customerId);

    void patchCustomerBydId(UUID customerId, Customer customer);
}
