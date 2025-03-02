package com.bank.tech.accounts.repository;

import com.bank.tech.accounts.audit.AuditAwareImp;
import com.bank.tech.accounts.config.TestJpaConfig;
import com.bank.tech.accounts.dto.CustomerDTO;
import com.bank.tech.accounts.entity.Customer;
import com.bank.tech.accounts.mapper.CustomerDataMapper;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.time.LocalDate;


@DisplayName("JUnit test for saving employee operation")
@Import(TestJpaConfig.class)
@SpringBootTest
@Transactional
@Slf4j
public class CustomerRepositoryTests {

    @Autowired
    CustomerRepository CUSTOMER_REPOSITORY;

    @Autowired
    CustomerDataMapper customerDataMapper;
    CustomerDTO customerDTO;

    @BeforeEach
public void init()
{
customerDTO = CustomerDTO.builder()
        .firstName("Mazen")
        .lastName("Ahmed")
        .mobileNumber("507156118")
        .dateOfBrith(LocalDate.of(1995,11,20))
        .build();
}
@Test
    public void givenCustomerDToThenSaveEmployee(){
        Customer customer= customerDataMapper.customerDTOToCustomerEntity(customerDTO);
      Customer savedCustomer=  CUSTOMER_REPOSITORY.save(customer);
    log.info("Customer ID: {}", savedCustomer.getId());
        Assertions.assertThat(savedCustomer).isNotNull();
    }
}
