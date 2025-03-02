package com.bank.tech.accounts.function;

import com.bank.tech.accounts.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.function.Consumer;

@Configuration
public class AccountsFunction {
    private static final Logger logger = LoggerFactory.getLogger(AccountsFunction.class);
    @Autowired
    CustomerService customerService;
    @Bean
            public Consumer<Long> updateCommunication(){

        return accountNumber->{
            logger.info("Updating customer account number {}", accountNumber);

            logger.info("Status customer account number {}", customerService.updateCustomerCommunication(accountNumber));
        };
    }

}
