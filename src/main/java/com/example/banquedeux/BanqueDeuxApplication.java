package com.example.banquedeux;

import com.example.banquedeux.dto.BankAccountDto;
import com.example.banquedeux.dto.CurrentAccountDto;
import com.example.banquedeux.dto.CustomerDto;
import com.example.banquedeux.dto.SavingAccountDto;
import com.example.banquedeux.entity.*;
import com.example.banquedeux.enums.AccountStatus;
import com.example.banquedeux.enums.OperationType;
import com.example.banquedeux.mappers.BankAccountmapperImpl;
import com.example.banquedeux.repository.AccountOperationRepository;
import com.example.banquedeux.repository.BankAccountRepository;
import com.example.banquedeux.repository.CustomerRepository;
import com.example.banquedeux.service.AccontOperationService;
import com.example.banquedeux.service.BankAccountService;
import com.example.banquedeux.service.CustomerService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class BanqueDeuxApplication {

    public static void main(String[] args) {
        SpringApplication.run(BanqueDeuxApplication.class, args);
    }
    //@Bean
    CommandLineRunner start(CustomerRepository customerRepository,
                            BankAccountRepository bankAccountRepository,
                            AccountOperationRepository accountOperationRepository){

        return args -> {
            Stream.of("Fatima","Ahmed","Assia","Kamel","Majid").forEach(name ->{
                Customer customer = new Customer();
                customer.setName(name);
                customer.setEmail(name+"gmail.com");
                customerRepository.save(customer);
            });

            customerRepository.findAll().forEach(customer -> {

                CurrentAccount currentAccount = new CurrentAccount();
                currentAccount.setId(UUID.randomUUID().toString());
                currentAccount.setCustomer(customer);
                currentAccount.setSattus(AccountStatus.CREATED);
                currentAccount.setBalance(Math.random()*1000);
                currentAccount.setCurrency("DA");
                currentAccount.setOverDraft(Math.random()*100);
                currentAccount.setCreateDate(new Date());
                currentAccount.setAccountOperations(currentAccount.getAccountOperations());
                bankAccountRepository.save(currentAccount);

                SavingAccount savingAccount = new SavingAccount();
                savingAccount.setId(UUID.randomUUID().toString());
                savingAccount.setCustomer(customer);
                savingAccount.setSattus(AccountStatus.CREATED);
                savingAccount.setBalance(Math.random()*1000);
                savingAccount.setCurrency("DA");
                savingAccount.setInterstRate(5.5);
                savingAccount.setCreateDate(new Date());
                savingAccount.setAccountOperations(savingAccount.getAccountOperations());
                bankAccountRepository.save(savingAccount);
            });

           bankAccountRepository.findAll().forEach(account ->{

               for(int i=0;i<10;i++){
                   AccountOperation accountOperation = new AccountOperation();
                   accountOperation.setBankAccount(account);
                   accountOperation.setAmount(accountOperation.getAmount());
                   accountOperation.setOperationDate(new Date());
                   accountOperation.setType(Math.random()>0.5?OperationType.CREDIT:OperationType.DEBIT);
                   accountOperation.setDescription(accountOperation.getType().equals(OperationType.CREDIT)?"credit":"debit");
                   accountOperationRepository.save(accountOperation);
               }

           });


        };


    }

    @Bean
    CommandLineRunner start(CustomerService customerService,
                            BankAccountService bankAccountService,
                            AccontOperationService accontOperationService,
                            BankAccountmapperImpl dtoMapper){
        return args -> {

            Stream.of("Fatima","Ahmed","Assia","Kamel","Majid").forEach(name ->{
                CustomerDto customer = new CustomerDto();
                customer.setName(name);
                customer.setEmail(name+"gmail.com");
                customerService.saveCustomer(customer);
            });

            customerService.listCustumers().forEach(customer ->{
                bankAccountService.saveCurrentBankAccount(Math.random()*100,300, customer.getId());
                bankAccountService.saveSavingBankAccount(Math.random(),5.5, customer.getId());

            });

           List<BankAccountDto> bankAccounts = bankAccountService.findAllBankAcount();
            for(BankAccountDto bankAccount:bankAccounts){
                String accountId = null;
                if(bankAccount instanceof CurrentAccountDto){
                    accountId = ((CurrentAccountDto) bankAccount).getId();
                } else if (bankAccount instanceof SavingAccountDto) {
                    accountId =((SavingAccountDto) bankAccount).getId();
                }

                accontOperationService.credit(accountId,Math.random()*100,"credit");
                accontOperationService.debit(accountId,Math.random()*10,"debit");
            }

        };
    }

}
