package com.example.spring6restmvc.service;

import com.example.spring6restmvc.model.CustomerDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerService {
    List<CustomerDTO> listCustomers();
    Optional<CustomerDTO> getCustomerById(UUID customerId);

    CustomerDTO createCustomer(CustomerDTO customer);

    void updateById(UUID customerId, CustomerDTO customer);

    void deleteBydId(UUID customerId);

    void patchCustomerBydId(UUID customerId, CustomerDTO customer);
}
