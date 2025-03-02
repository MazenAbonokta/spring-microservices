package com.bank.tech.accounts.service;

import com.bank.tech.accounts.dto.AccountDTO;
import com.bank.tech.accounts.entity.Account;

import java.util.Optional;

public interface AccountService {

    void createAccount(AccountDTO accountDTO);



    Account getAccountByNumber(String accountNumber);


    /**
     *

     * @return boolean indicating if the update of Account details is successful or not
     */
    boolean updateAccount(AccountDTO  accountDTO);

    /**
     *
     * @param mobileNumber - Input Mobile Number
     * @return boolean indicating if the delete of Account details is successful or not
     */
    boolean deleteAccount(String mobileNumber);
}
