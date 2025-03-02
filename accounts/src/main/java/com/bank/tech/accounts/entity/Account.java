package com.bank.tech.accounts.entity;

import com.bank.tech.accounts.dto.AccountType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Account extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    @JoinColumn(name="customer_id")
    @ManyToOne(cascade = CascadeType.ALL)
    private Customer customer;


    private int balance;

    private boolean isBlocked = Boolean.FALSE;

    @Enumerated(EnumType.STRING)

    private AccountType accountType;

    @Column(length = 18)
    private String accountNumber;


}
