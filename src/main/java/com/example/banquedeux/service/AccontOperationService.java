package com.example.banquedeux.service;

import com.example.banquedeux.dto.AccountHistoryDto;
import com.example.banquedeux.dto.AccountOperationDto;
import com.example.banquedeux.entity.AccountOperation;

import java.util.List;

public interface AccontOperationService {
    void debit(String acountId, double amout, String description);
    void credit(String acountId, double amout, String description);
    void transfert(String accountIdSource, String accountIdDestination, double amount);
    List<AccountOperationDto> accountHistorique(String accountId);
    List<AccountOperationDto> findAllOperations();

    AccountHistoryDto getAccountHistorique(String id, int page, int size);


}
