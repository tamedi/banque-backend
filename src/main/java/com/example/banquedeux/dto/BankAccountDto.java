package com.example.banquedeux.dto;

import com.example.banquedeux.entity.AccountOperation;
import com.example.banquedeux.entity.Customer;
import com.example.banquedeux.enums.AccountStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class BankAccountDto {
    String type;
}
