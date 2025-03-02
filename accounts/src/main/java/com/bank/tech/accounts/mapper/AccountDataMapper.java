package com.bank.tech.accounts.mapper;

import com.bank.tech.accounts.dto.AccountDTO;
import com.bank.tech.accounts.entity.Account;
import com.bank.tech.accounts.entity.Customer;
import org.springframework.stereotype.Component;

@Component
public class AccountDataMapper {

    public Account accountDTOToAccount(AccountDTO accountDTO, Customer customer)
    {
        return  Account.builder()
                .customer(customer)
                .accountNumber(accountDTO.getAccountNumber())
                .accountType(accountDTO.getAccountType())
                .balance(accountDTO.getBalance())
                .build();
    }
    public AccountDTO accountToAccountDTO(Account account)
    {
        return  AccountDTO.builder()

                .accountNumber(account.getAccountNumber())
                .accountType(account.getAccountType())
                .balance(account.getBalance())
                .build();
    }
}
