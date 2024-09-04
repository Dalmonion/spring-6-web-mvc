package com.example.spring6restmvc.service;

import com.example.spring6restmvc.mapper.CustomerMapper;
import com.example.spring6restmvc.model.CustomerDTO;
import com.example.spring6restmvc.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

@Service
@Primary
@RequiredArgsConstructor
public class CustomerServiceJPA implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private final CacheManager cacheManager;

    @Cacheable(cacheNames = "customerListCache")
    @Override
    public List<CustomerDTO> listCustomers() {
        return customerRepository.findAll().stream()
                .map(customerMapper::customerToCustomerDto)
                .toList();
    }

    @Cacheable(cacheNames = "customerCache")
    @Override
    public Optional<CustomerDTO> getCustomerById(UUID customerId) {
        return Optional.ofNullable(customerMapper.customerToCustomerDto(
                customerRepository.findById(customerId).orElse(null)));
    }

    @Override
    public CustomerDTO createCustomer(CustomerDTO customer) {
        if (cacheManager.getCache("customerListCache") != null) {
            cacheManager.getCache("customerListCache").clear();
        }
        return customerMapper.customerToCustomerDto(customerRepository.save(customerMapper.customerDtoToCustomer(
                customer)));
    }

    @Override
    public Optional<CustomerDTO> updateById(UUID customerId, CustomerDTO customer) {
        clearCache(customerId);
        AtomicReference<Optional<CustomerDTO>> atomicReference = new AtomicReference<>();
        customerRepository.findById(customerId).ifPresentOrElse(foundedCustomer -> {
            foundedCustomer.setCustomerName(customer.getCustomerName());
            atomicReference.set(Optional.of(customerMapper.customerToCustomerDto(customerRepository.save(foundedCustomer))));
        }, () -> {
            atomicReference.set(Optional.empty());
        });
        return atomicReference.get();
    }

    private void clearCache(UUID customerId) {
        if (cacheManager.getCache("customerListCache") != null) {
            cacheManager.getCache("customerListCache").clear();
        }
        if (cacheManager.getCache("customerCache") != null) {
            cacheManager.getCache("customerCache").evict(customerId);
        }
    }

    @Override
    public Boolean deleteBydId(UUID customerId) {
        clearCache(customerId);
        if (customerRepository.existsById(customerId)) {
            customerRepository.deleteById(customerId);
            return true;
        }
        return false;
    }

    @Override
    public Optional<CustomerDTO> patchCustomerBydId(UUID customerId, CustomerDTO customer) {
        clearCache(customerId);
        AtomicReference<Optional<CustomerDTO>> atomicReference = new AtomicReference<>();
        customerRepository.findById(customerId).ifPresentOrElse(foundedCustomer -> {
            if (customer.getCustomerName() != null) {
                foundedCustomer.setCustomerName(customer.getCustomerName());
            }
            atomicReference.set(Optional.of(customerMapper.customerToCustomerDto(customerRepository.save(foundedCustomer))));
        }, () -> {
            atomicReference.set(Optional.empty());
        });
        return atomicReference.get();
    }
}
