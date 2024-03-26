package com.example.banquedeux.dto;

import lombok.Data;

import java.util.List;

@Data
public class AccountHistoryDto {
    private String accountId;
    private double balance;
    private int currentPage;
    private  int totalePages;
    private int size;
    private List<AccountOperationDto> accountOperationDtos;
}
