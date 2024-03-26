package com.example.banquedeux.dto;

import com.example.banquedeux.entity.BankAccount;
import com.example.banquedeux.enums.OperationType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.util.Date;

@Data
public class AccountOperationDto {
    private Long id;
    private Date operationDate;
    private double amount;
    private OperationType type;
    private BankAccountDto bankAccountDto;
    private String description;
}
