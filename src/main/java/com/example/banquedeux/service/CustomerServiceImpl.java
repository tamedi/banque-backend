package com.example.banquedeux.service;

import com.example.banquedeux.dto.CustomerDto;
import com.example.banquedeux.entity.Customer;
import com.example.banquedeux.mappers.BankAccountmapperImpl;
import com.example.banquedeux.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService{

    private CustomerRepository customerRepository;
    private BankAccountmapperImpl dtoMapper;

    @Override
    public CustomerDto saveCustomer(CustomerDto customerDto) {
       Customer customer = dtoMapper.fromCustomerDto(customerDto);
        Customer savedCustomer = customerRepository.save(customer);
        CustomerDto savedCustomerDto = dtoMapper.fromCustomer(savedCustomer);
        return savedCustomerDto;
    }

    @Override
    public List<CustomerDto> listCustumers() {
        List<Customer> customers = customerRepository.findAll();
        List<CustomerDto> customerDtos = customers.stream().map(customer -> dtoMapper.fromCustomer(customer)).collect(Collectors.toList());
        return customerDtos;
    }

    @Override
    public CustomerDto getCustomer(Long customerId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(()->new RuntimeException(" customer not fount"));
        CustomerDto customerDto = dtoMapper.fromCustomer(customer);
        return customerDto;
    }

    @Override
    public Customer updateCustomer(Customer customer, Long customerid) {
        /*Customer exitingCustomer = getCustomer(customerid);
        exitingCustomer.setName(customer.getName()!=null? customer.getName() : exitingCustomer.getName());
        exitingCustomer.setEmail(customer.getEmail()!=null? customer.getEmail() : exitingCustomer.getEmail());*/
        return null;
    }

    @Override
    public void deleteCustomer(Long customerId) {
        customerRepository.deleteById(customerId);
    }

    @Override
    public List<CustomerDto> searchCustomers(String keyword) {
        List<Customer> customers = customerRepository.searchCustomer(keyword);
        List<CustomerDto> customerDtos = customers.stream().map(customer -> dtoMapper.fromCustomer(customer)).collect(Collectors.toList());
        return customerDtos;
    }
}
