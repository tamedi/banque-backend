package com.example.banquedeux.service;

import com.example.banquedeux.dto.*;
import com.example.banquedeux.entity.AccountOperation;
import com.example.banquedeux.entity.BankAccount;
import com.example.banquedeux.enums.OperationType;
import com.example.banquedeux.exception.BankAccountNotFoundException;
import com.example.banquedeux.mappers.BankAccountmapperImpl;
import com.example.banquedeux.repository.AccountOperationRepository;
import com.example.banquedeux.repository.BankAccountRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Transactional
@AllArgsConstructor
public class AccontOperationServiceImpl implements AccontOperationService{

    private AccountOperationRepository accountOperationRepository;
    private BankAccountRepository bankAccountRepository;
    private BankAccountmapperImpl dtoMapper;

    @Override
    public void debit(String acountId, double amout, String description) {

        BankAccount bankAcount = bankAccountRepository.findById(acountId).orElseThrow(()->new RuntimeException(""));
        if(amout>bankAcount.getBalance()){
            new RuntimeException(" solde insuffisant");
        }else {
            AccountOperation accountOperation = new AccountOperation();
            accountOperation.setBankAccount(bankAcount);
            accountOperation.setOperationDate(new Date());
            accountOperation.setAmount(amout);
            accountOperation.setDescription(description);
            accountOperation.setType(OperationType.DEBIT);
            accountOperationRepository.save(accountOperation);
            bankAcount.setBalance(bankAcount.getBalance()-amout);
            bankAccountRepository.save(bankAcount);

        }
    }

    @Override
    public void credit(String acountId, double amout, String description) {

        BankAccount bankAcount = bankAccountRepository.findById(acountId).orElseThrow(()->new RuntimeException(""));
        AccountOperation accountOperation = new AccountOperation();
        accountOperation.setOperationDate(new Date());
        accountOperation.setAmount(amout);
        accountOperation.setBankAccount(bankAcount);
        accountOperation.setDescription(description);
        accountOperation.setType(OperationType.CREDIT);
        accountOperationRepository.save(accountOperation);
        bankAcount.setBalance(bankAcount.getBalance()+amout);
        bankAccountRepository.save(bankAcount);


    }

    @Override
    public void transfert(String accountIdSource, String accountIdDestination, double amount) {

        debit(accountIdSource,amount,"trsfer to"+accountIdDestination);
        credit(accountIdDestination,amount,"transfer from"+accountIdDestination);

    }

    @Override
    public List<AccountOperationDto> accountHistorique(String accountId) {

        List<AccountOperation> accountOperations = accountOperationRepository.findByBankAccountId(accountId);
        List<AccountOperationDto> accountOperationDtos = accountOperations.stream().map(opp -> dtoMapper.fromAccountOperation(opp)).collect(Collectors.toList());
        return accountOperationDtos;
    }

    @Override
    public List<AccountOperationDto> findAllOperations() {
        List<AccountOperation> accountOperations = accountOperationRepository.findAll();
        List<AccountOperationDto> accountOperationDtos = accountOperations.stream().map(opp -> dtoMapper.fromAccountOperation(opp)).collect(Collectors.toList());
        return accountOperationDtos;
    }



    @Override
    public AccountHistoryDto getAccountHistorique(String id, int page, int size) {

        BankAccount bankAccount= bankAccountRepository.findById(id)
                .orElseThrow(()->new BankAccountNotFoundException("this account does'nt existe"));

        Page<AccountOperation> accoutoperations = accountOperationRepository.findByBankAccountIdOrderByOperationDateDesc(id, PageRequest.of(page, size));

        AccountHistoryDto accountHistoryDto = new AccountHistoryDto();
        List<AccountOperationDto> accountOperationDtos = accoutoperations.getContent().stream().map(op->dtoMapper.fromAccoutOperation(op)).collect(Collectors.toList());
        accountHistoryDto.setAccountOperationDtos(accountOperationDtos);
        accountHistoryDto.setAccountId(bankAccount.getId());
        accountHistoryDto.setBalance(bankAccount.getBalance());
        accountHistoryDto.setSize(size);
        accountHistoryDto.setCurrentPage(page);
        accountHistoryDto.setTotalePages(accoutoperations.getTotalPages());
        return accountHistoryDto;
    }
}
