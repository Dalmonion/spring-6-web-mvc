package com.example.spring6restmvc.service;

import com.example.spring6restmvc.mapper.CustomerMapper;
import com.example.spring6restmvc.model.CustomerDTO;
import com.example.spring6restmvc.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Primary
@RequiredArgsConstructor
public class CustomerServiceJPA implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Override
    public List<CustomerDTO> listCustomers() {
        return null;
    }

    @Override
    public Optional<CustomerDTO> getCustomerById(UUID customerId) {
        return Optional.empty();
    }

    @Override
    public CustomerDTO createCustomer(CustomerDTO customer) {
        return null;
    }

    @Override
    public void updateById(UUID customerId, CustomerDTO customer) {

    }

    @Override
    public void deleteBydId(UUID customerId) {

    }

    @Override
    public void patchCustomerBydId(UUID customerId, CustomerDTO customer) {

    }
}
