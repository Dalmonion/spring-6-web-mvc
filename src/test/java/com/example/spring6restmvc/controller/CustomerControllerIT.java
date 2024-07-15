package com.example.spring6restmvc.controller;

import com.example.spring6restmvc.entity.Customer;
import com.example.spring6restmvc.model.CustomerDTO;
import com.example.spring6restmvc.repository.CustomerRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class CustomerControllerIT {

    @Autowired
    private CustomerController customerController;

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    void testGetCustomerById() {
        Customer customer = customerRepository.findAll().get(0);

        CustomerDTO dto = customerController.getCustomerById(customer.getId());

        assertThat(dto).isNotNull();
    }

    @Test
    void testGetCustomerByIdNotFound() {
        assertThrows(NotFoundException.class, () -> {
            customerController.getCustomerById(UUID.randomUUID());
        });
    }

    @Test
    void testGetCustomerList() {
        List<CustomerDTO> customerDTOList = customerController.listAllCustomers();

        assertThat(customerDTOList).isNotNull();
        assertThat(customerDTOList.size()).isEqualTo(3);
    }

    @Transactional
    @Test
    void testGetCustomerListEmpty() {
        customerRepository.deleteAll();
        List<CustomerDTO> customerDTOList = customerController.listAllCustomers();

        assertThat(customerDTOList).isEmpty();
    }
}