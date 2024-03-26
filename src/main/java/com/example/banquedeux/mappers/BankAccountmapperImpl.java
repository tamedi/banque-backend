package com.example.banquedeux.mappers;

import com.example.banquedeux.dto.*;
import com.example.banquedeux.entity.*;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class BankAccountmapperImpl {

    public CurrentAccountDto fromCurrentBankAccount(CurrentAccount currentAccount){
        CurrentAccountDto currentAccountDto = new CurrentAccountDto();
        BeanUtils.copyProperties(currentAccount,currentAccountDto);
        currentAccountDto.setCustomerDto(fromCustomer(currentAccount.getCustomer()));
        return currentAccountDto;
    }

    public SavingAccountDto fromSavingAccount(SavingAccount savingAccount){
        SavingAccountDto savingAccountDto = new SavingAccountDto();
        BeanUtils.copyProperties(savingAccount,savingAccountDto);
        savingAccountDto.setCustomerDto(fromCustomer(savingAccount.getCustomer()));
        return savingAccountDto;
    }

    public CustomerDto fromCustomer(Customer customer){
        CustomerDto customerDto = new CustomerDto();
        BeanUtils.copyProperties(customer,customerDto);
        return customerDto;
    }

    public Customer fromCustomerDto(CustomerDto customerDto){
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDto,customer);
        return customer;
    }

    public CurrentAccount fromCurrentBankAccountDto(CurrentAccountDto currentAccountDto) {
        CurrentAccount currentAccount = new CurrentAccount();
        BeanUtils.copyProperties(currentAccountDto,currentAccount);
        currentAccount.setCustomer(fromCustomerDto(currentAccountDto.getCustomerDto()));
        return currentAccount;
    }

    public SavingAccount fromSavingBankAccountDto(SavingAccountDto savingAccountDto) {
        SavingAccount savingAccount = new SavingAccount();
        BeanUtils.copyProperties(savingAccountDto,savingAccount);
        savingAccount.setCustomer(fromCustomerDto(savingAccountDto.getCustomerDto()));
        return savingAccount;
    }

    public AccountOperationDto fromAccountOperation (AccountOperation accountOperation){
        AccountOperationDto accountOperationDto = new AccountOperationDto();
        BeanUtils.copyProperties(accountOperation,accountOperationDto);
        accountOperationDto.setBankAccountDto(fromBankAccout(accountOperation.getBankAccount()));
        return accountOperationDto;
    }

    private BankAccountDto fromBankAccout(BankAccount bankAccount) {
        BankAccountDto bankAccountDto = new BankAccountDto();

        bankAccountDto.setType(bankAccount.getClass().getSimpleName());
        return bankAccountDto;
    }

    public AccountOperationDto fromAccoutOperation(AccountOperation accountOperation){
        AccountOperationDto accountOperationDto = new AccountOperationDto();
        BeanUtils.copyProperties(accountOperation,accountOperationDto);
        return accountOperationDto;
    }
}
