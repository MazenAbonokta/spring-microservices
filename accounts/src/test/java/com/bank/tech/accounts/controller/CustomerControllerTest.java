package com.bank.tech.accounts.controller;

import com.bank.tech.accounts.dto.CustomerDTO;
import com.bank.tech.accounts.entity.Customer;
import com.bank.tech.accounts.mapper.CustomerDataMapper;
import com.bank.tech.accounts.model.CustomerRequest;
import com.bank.tech.accounts.repository.CustomerRepository;
import com.bank.tech.accounts.service.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static reactor.core.publisher.Mono.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableConfigurationProperties
@AutoConfigureMockMvc
public class CustomerControllerTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    CustomerService customerService;
    @Autowired
    ObjectMapper objectMapper;
    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private CustomerDataMapper customerDataMapper;
    private CustomerDTO customerDTO;

    @BeforeEach
    void init(){
        customerDTO = new CustomerDTO(){};

        customerDTO.setEmail("mazen@gmail.com");
        customerDTO.setFirstName("Maze");
        customerDTO.setLastName("Maze");
        customerDTO.setDateOfBrith(LocalDate.of(1996,1,1));
        customerDTO.setMobileNumber("0807156118");
        customerDTO.setAccounts(new ArrayList<>());
    }

    @Test
    public void testCreateCustomer() throws Exception {

        Customer customer = new Customer();
        customer.setId(12345);

        Mockito.when(customerDataMapper.customerDTOToCustomerEntity(customerDTO)).thenReturn(customer);

        Mockito.when(customerService.createCustomer(customerDTO)).thenReturn(customer);

       /* BDDMockito.given(customerService.createCustomer(ArgumentMatchers.any(CustomerDTO.class)))
                .willAnswer(invocationOnMock -> invocationOnMock.getArgument(0));*/

        CustomerRequest customerRequest = CustomerRequest.builder()
                .firstName("Maze")
                .email("mazen@gmail.com")
                .mobileNumber(customerDTO.getMobileNumber())
                .dateOfBrith(customerDTO.getDateOfBrith())
                .lastName("Maze")
                .build();



String ff =objectMapper.writeValueAsString(customerDTO);
        ResultActions resultActions =mockMvc.perform(post("/api/customer/create")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(ff));

        resultActions.andDo(print())
                .andExpect(status().isCreated());
            /*    .andExpect(jsonPath("$.firstName",
                        CoreMatchers.is(customerDTO.getFirstName())))
                .andExpect(jsonPath("$.lastName",
                        CoreMatchers.is(customerDTO.getLastName())))
                .andExpect(jsonPath("$.email",
                        CoreMatchers.is(customerDTO.getEmail())));*/

    }
}