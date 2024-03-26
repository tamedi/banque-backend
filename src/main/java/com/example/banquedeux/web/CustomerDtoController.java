package com.example.banquedeux.web;

import com.example.banquedeux.dto.CustomerDto;
import com.example.banquedeux.entity.Customer;
import com.example.banquedeux.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin("*")
public class CustomerDtoController {

    private CustomerService customerService;

    @GetMapping("customers")
    public List<CustomerDto> findAllCostumer(){
        return customerService.listCustumers();
    }

    @GetMapping("customers/search")
    public List<CustomerDto> searchCustumer(@RequestParam(name = "keyword", defaultValue = "") String keyword){
        return customerService.searchCustomers("%" + keyword + "%");
    }

    @PostMapping("customers")
    public CustomerDto saveCustomer(@RequestBody CustomerDto customerDto){
        return customerService.saveCustomer(customerDto);
    }

    @GetMapping("customers/{id}")
    public CustomerDto findCostumerById(@PathVariable Long id){
        return customerService.getCustomer(id);
    }

    @PutMapping("/customers/{id}")
    public CustomerDto updateCustomer(@PathVariable Long id, @RequestBody CustomerDto request) {
        request.setId(id);
        return customerService.saveCustomer(request);
    }

    @DeleteMapping("/customers/{id}")
    public void deleteCustomer(@PathVariable Long id){
        customerService.deleteCustomer(id);
    }
}
