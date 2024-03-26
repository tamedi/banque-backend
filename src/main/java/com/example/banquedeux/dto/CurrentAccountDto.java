package com.example.banquedeux.dto;

import com.example.banquedeux.entity.BankAccount;
import com.example.banquedeux.entity.Customer;
import com.example.banquedeux.enums.AccountStatus;
import lombok.Data;

import java.util.Date;
@Data
public class CurrentAccountDto extends BankAccountDto {
    private String id;
    private double balance;
    private Date createDate;
    private AccountStatus sattus;
    private String currency;
    private double overDraft;
    private CustomerDto customerDto;
}
