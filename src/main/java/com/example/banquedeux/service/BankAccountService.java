package com.example.banquedeux.service;

import com.example.banquedeux.dto.BankAccountDto;
import com.example.banquedeux.dto.CurrentAccountDto;
import com.example.banquedeux.dto.SavingAccountDto;
import com.example.banquedeux.entity.*;

import java.util.List;

public interface BankAccountService {

    CurrentAccountDto saveCurrentBankAccount(double initialBalance, double overDraft, Long customerId);
    SavingAccountDto saveSavingBankAccount(double initialBalance, double interestRate, Long customerId);
    BankAccountDto getBankAcount(String accountId);
    List<BankAccountDto> findAllBankAcount();
}
