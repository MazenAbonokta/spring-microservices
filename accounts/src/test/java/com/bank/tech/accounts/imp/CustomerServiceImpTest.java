package com.bank.tech.accounts.imp;

import com.bank.tech.accounts.config.TestJpaConfig;
import com.bank.tech.accounts.dto.CustomerDTO;
import com.bank.tech.accounts.entity.Customer;
import com.bank.tech.accounts.exception.ResourceNotFoundException;
import com.bank.tech.accounts.mapper.CustomerDataMapper;
import com.bank.tech.accounts.repository.CustomerRepository;
import com.bank.tech.accounts.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;


@Slf4j
@Import(value = {TestJpaConfig.class})
@SpringBootTest
public class CustomerServiceImpTest {
    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private CustomerDataMapper customerDataMapper;
    @Mock
    private  StreamBridge streamBridge;

    private CustomerDTO customerDTO;

    @BeforeEach
    void init(){
        customerDTO = new CustomerDTO(){};

        customerDTO.setEmail("mazen@gmail.com");
        customerDTO.setFirstName("Maze");
        customerDTO.setLastName("Maze");
        customerDTO.setDateOfBrith(LocalDate.of(1996,1,1));
        customerDTO.setMobileNumber("0807156118");
    }

    @InjectMocks
    CustomerService customerService =new CustomerServiceImp();

    @DisplayName("Creat Customer")
    @Test
    void test_createCustomer() {
        //Mocking

        //Actual

        Customer customer = new Customer();
        customer.setId(12345);
        when(customerDataMapper.customerDTOToCustomerEntity(customerDTO)).thenReturn(customer);

        when(customerRepository.save(customer)).thenReturn(customer);
        Customer savedCustomer=  customerRepository.save(customer);
        log.info("Customer ID: {}", savedCustomer.getId());
        customer = customerService.createCustomer(customerDTO);
        //Verification
        log.info(customer.toString());
        Assertions.assertThat(customer).isNotNull();
        //Assertions.assertThat(customer.getFirstName()).isEqualTo("Maze");
        //Assert
    }

    @Test
    void  test_when_find_Customer_not_fount(){
        Customer customer = new Customer();
        when(customerRepository.findById(anyLong())).thenReturn(Optional.empty());
        ResourceNotFoundException exception=
                assertThrows(ResourceNotFoundException.class,
                        ()->customerService.getCustomerById(1L));
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains("Customer"));
    }
}