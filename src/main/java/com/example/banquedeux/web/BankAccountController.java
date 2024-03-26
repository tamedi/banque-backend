package com.example.banquedeux.web;

import com.example.banquedeux.dto.BankAccountDto;
import com.example.banquedeux.entity.BankAccount;
import com.example.banquedeux.service.BankAccountService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin("*")
public class BankAccountController {

    private BankAccountService bankAccountService;

    @GetMapping("accounts")
    public List<BankAccountDto> findAllBankAccount(){
        return bankAccountService.findAllBankAcount();
    }

    @GetMapping("accounts/{id}")
    public BankAccountDto findbankAccountById(@PathVariable String id){
        return bankAccountService.getBankAcount(id);
    }
}
