package com.bank.tech.accounts.mapper;

import com.bank.tech.accounts.dto.AddressDTO;
import com.bank.tech.accounts.dto.CustomerDTO;
import com.bank.tech.accounts.entity.Address;
import com.bank.tech.accounts.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class CustomerDataMapper {

    @Autowired
    AccountDataMapper accountDataMapper;

    public Customer customerDTOToCustomerEntity(CustomerDTO customerDTO){
        return Customer.builder()
                .lastName(customerDTO.getLastName())
                .email(customerDTO.getEmail())
                .firstName(customerDTO.getFirstName())
                .dateOfBrith(customerDTO.getDateOfBrith())
                .mobileNumber(customerDTO.getMobileNumber())
                .build();
    }
    public CustomerDTO customerToCustomerDTO(Customer customer){
        return CustomerDTO.builder()
                .lastName(customer.getLastName())
                .email(customer.getEmail())
                .firstName(customer.getFirstName())
                .dateOfBrith(customer.getDateOfBrith())
                .mobileNumber(customer.getMobileNumber())
                .accounts(customer.getAccounts().stream().map(accountDataMapper::accountToAccountDTO)
                        .collect(Collectors.toList())
                )
                .build();
    }


    public Address addressDTOToAddressEntity(AddressDTO addressDTO,Customer customer){
        return Address.builder()
                .customer(customer)
                .addressType(addressDTO.getAddressType())
                .city(addressDTO.getCity())
                .region(addressDTO.getRegion())
                .build();
    }
}
