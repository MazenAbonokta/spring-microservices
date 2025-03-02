package com.bank.tech.accounts.dto;

import com.bank.tech.accounts.entity.Customer;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountDTO {

    private Long customerId;
    private int balance;
    private AccountType accountType;
    private String accountNumber;
}
