package com.bank.tech.accounts.service;

import com.bank.tech.accounts.dto.CustomerDTO;
import com.bank.tech.accounts.entity.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerService {
    public Customer createCustomer(CustomerDTO customerDTO);
    public Customer getCustomerById(Long customerIDd);

    public List<CustomerDTO> getCustomers(String correlationId);

    public  CustomerDTO getCustomerDetails(String mobileNumber);

    public void deleteCustomer(String mobileNumber);
    public void sendCommunication(Customer customer) ;

    public boolean updateCustomerCommunication(long id);
}
