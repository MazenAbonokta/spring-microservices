package com.bank.tech.accounts;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootTest

@ComponentScan(basePackages = "com.bank.tech.accounts")
class AccountsApplicationTests {

    @Test
    void contextLoads() {
    }

}
