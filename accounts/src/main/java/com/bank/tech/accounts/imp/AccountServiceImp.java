package com.bank.tech.accounts.imp;

import com.bank.tech.accounts.dto.AccountDTO;
import com.bank.tech.accounts.entity.Account;
import com.bank.tech.accounts.entity.Customer;
import com.bank.tech.accounts.exception.AccountAlreadyExistException;
import com.bank.tech.accounts.exception.CustomerAlreadyExistException;
import com.bank.tech.accounts.mapper.AccountDataMapper;
import com.bank.tech.accounts.repository.AccountRepository;
import com.bank.tech.accounts.service.AccountService;
import com.bank.tech.accounts.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountServiceImp implements AccountService {
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    CustomerService customerService;
    @Autowired
    AccountDataMapper mapper;

    @Override
    public void createAccount(AccountDTO accountDTO) {

        Customer customer= customerService.getCustomerById(accountDTO.getCustomerId());
        Account  account =mapper.accountDTOToAccount(accountDTO,customer);
        accountRepository.save(account);

    }

    @Override
    public Account getAccountByNumber(String accountNumber) {

        Optional<Account> account= accountRepository.getAccountByAccountNumber(accountNumber);
        if (account.isPresent())
        {
            return account.get();
        }
        return null;
    }

    @Override
    public boolean updateAccount(AccountDTO accountDTO) {
        return false;
    }

    @Override
    public boolean deleteAccount(String mobileNumber) {
       /* Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );
        accountsRepository.deleteByCustomerId(customer.getCustomerId());
        customerRepository.deleteById(customer.getCustomerId());*/

        return true;
    }


    private void validateExistNumber(String accountNumber){
        Account  account= this.getAccountByNumber( accountNumber);
        if(account !=null)
        {
            throw new AccountAlreadyExistException("The account number"+ accountNumber+ " is already exist");
        }
    }
    @Scheduled(fixedRate = 5000)

    /*Should add @WnableSchedueling in Main Class*/
    public void senMessage(){
        System.out.println("send");
    }

}
