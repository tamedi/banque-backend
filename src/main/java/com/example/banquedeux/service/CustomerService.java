package com.example.banquedeux.service;

import com.example.banquedeux.dto.CustomerDto;
import com.example.banquedeux.entity.Customer;

import java.util.List;

public interface CustomerService {

    CustomerDto saveCustomer(CustomerDto customerDto);
    List<CustomerDto> listCustumers();
    CustomerDto getCustomer(Long customerId);
    Customer updateCustomer(Customer customer, Long customerid);
    void deleteCustomer(Long customerId);

    List<CustomerDto> searchCustomers(String keyword);
}
