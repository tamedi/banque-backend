package com.example.banquedeux.service;

import com.example.banquedeux.dto.BankAccountDto;
import com.example.banquedeux.dto.CurrentAccountDto;
import com.example.banquedeux.dto.CustomerDto;
import com.example.banquedeux.dto.SavingAccountDto;
import com.example.banquedeux.entity.BankAccount;
import com.example.banquedeux.entity.CurrentAccount;
import com.example.banquedeux.entity.Customer;
import com.example.banquedeux.entity.SavingAccount;
import com.example.banquedeux.enums.AccountStatus;
import com.example.banquedeux.mappers.BankAccountmapperImpl;
import com.example.banquedeux.repository.BankAccountRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Transactional
@AllArgsConstructor
public class BankAccountServiceImpl implements BankAccountService{

    private BankAccountRepository bankAccountRepository;
    private CustomerService customerService;

    private BankAccountmapperImpl dtoMapper;

   @Override
    public CurrentAccountDto saveCurrentBankAccount(double initialBalance, double overDraft, Long customerId) {

        CustomerDto customer = customerService.getCustomer(customerId);

        CurrentAccount currentAccount = new CurrentAccount();

        currentAccount.setId(UUID.randomUUID().toString());
        currentAccount.setBalance(initialBalance);
        currentAccount.setCreateDate(new Date());
        currentAccount.setSattus(AccountStatus.CREATED);
        currentAccount.setCurrency("DA");
        currentAccount.setCustomer(dtoMapper.fromCustomerDto(customer));
        currentAccount.setOverDraft(overDraft);
        CurrentAccount savedCurrentAccount = bankAccountRepository.save(currentAccount);

        return null;
    }

    @Override
    public SavingAccountDto saveSavingBankAccount(double initialBalance, double interestRate, Long customerId) {

        CustomerDto customerDto =customerService.getCustomer(customerId);

        SavingAccount savingAccount = new SavingAccount();

        savingAccount.setId(UUID.randomUUID().toString());
        savingAccount.setBalance(initialBalance);
        savingAccount.setInterstRate(interestRate);
        savingAccount.setCustomer(dtoMapper.fromCustomerDto(customerDto));
        savingAccount.setCreateDate(new Date());
        savingAccount.setCurrency("DA");
        savingAccount.setSattus(AccountStatus.CREATED);
        SavingAccount savedSavingAccount = bankAccountRepository.save(savingAccount);
        return null;
    }



    @Override
    public BankAccountDto getBankAcount(String accountId) {
        BankAccount bankAccount = bankAccountRepository.findById(accountId).orElseThrow(() -> new RuntimeException());

        if(bankAccount instanceof CurrentAccount){
            CurrentAccount currentAccount = (CurrentAccount) bankAccount;

            return dtoMapper.fromCurrentBankAccount(currentAccount);
        } else {
            SavingAccount savingAccount = (SavingAccount) bankAccount;
            return dtoMapper.fromSavingAccount(savingAccount);

        }


    }

    @Override
    public List<BankAccountDto> findAllBankAcount() {
        List<BankAccount> bankAccounts= bankAccountRepository.findAll();
        List<BankAccountDto> bankAccountDtos = bankAccounts
                .stream()
                .map(account ->{
                    if(account instanceof CurrentAccount){
                        return dtoMapper.fromCurrentBankAccount((CurrentAccount) account);
                    } else  {
                       return dtoMapper.fromSavingAccount((SavingAccount) account);
                    }

                } ).collect(Collectors.toList());

        return bankAccountDtos;
    }
}

