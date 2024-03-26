package com.example.banquedeux.web;

import com.example.banquedeux.dto.*;
import com.example.banquedeux.entity.AccountOperation;
import com.example.banquedeux.service.AccontOperationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin("*")
public class AccountOperationController {

    private AccontOperationService accontOperationService;

    @GetMapping("accounts/operations/{id}")
    public List<AccountOperationDto> accountHistorique(@PathVariable String id){
        return accontOperationService.accountHistorique(id);
    }

    @GetMapping("accounts/operations")
    public List<AccountOperationDto> accountHistorique(){
        return accontOperationService.findAllOperations();
    }

    @GetMapping("accounts/{id}/pageOperations")
    public AccountHistoryDto getAccountHistorique(@PathVariable String id,
                                                  @RequestParam(name = "page",defaultValue = "0") int page,
                                                  @RequestParam(name = "size",defaultValue = "5")int size){
        return accontOperationService.getAccountHistorique(id,page,size);
    }

    @PostMapping("/accounts/debit")
    public DebitDto debit(@RequestBody DebitDto debitDto){
        accontOperationService.debit(debitDto.getAccountId(), debitDto.getAmount(), debitDto.getDescription());
        return debitDto;
    }

    @PostMapping("/accounts/credit")
    public CreditDto credit(@RequestBody CreditDto creditDto){
        accontOperationService.credit(creditDto.getAccountId(), creditDto.getAmount(), creditDto.getDescription());
        return creditDto;
    }

    @PostMapping("/accounts/transfer")
    public void transfer(@RequestBody TransferRequestDto transferRequestDto){
        accontOperationService.transfert(transferRequestDto.getAccountSource(), transferRequestDto.getAccountDestination(), transferRequestDto.getAmount());

    }
}
