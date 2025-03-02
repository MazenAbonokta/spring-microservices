package com.bank.tech.accounts.imp;

import com.bank.tech.accounts.dto.AccountMessageDto;
import com.bank.tech.accounts.dto.CustomerDTO;
import com.bank.tech.accounts.entity.Customer;
import com.bank.tech.accounts.exception.CustomerAlreadyExistException;
import com.bank.tech.accounts.exception.ResourceNotFoundException;
import com.bank.tech.accounts.mapper.CustomerDataMapper;
import com.bank.tech.accounts.repository.CustomerRepository;
import com.bank.tech.accounts.service.CustomerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class CustomerServiceImp implements CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerDataMapper customerDataMapper;
    @Autowired
    private  StreamBridge streamBridge;

    public CustomerServiceImp() {

    }


    @Override
    public Customer createCustomer(CustomerDTO customerDTO) {

        customerDTO.validateDateOfBrith();
        CheckCustomer(customerDTO.getMobileNumber());
        Customer customer= customerDataMapper.customerDTOToCustomerEntity(customerDTO);

        Customer createdCustomer= customerRepository.save(customer);
        sendCommunication(createdCustomer);
        return createdCustomer;
    }

    private void CheckCustomer(String mobileNumber){
        Optional<Customer> customer = customerRepository.findByMobileNumber(mobileNumber);

        if(customer.isPresent())
        {
            throw  new CustomerAlreadyExistException("the mobile number is already exists:" + mobileNumber);
        }
    }
    public Customer getCustomerById(Long customerID){
        Customer customer = customerRepository.findById(customerID).orElseThrow(

                ()->    new ResourceNotFoundException("Customer","Id",customerID.toString()));

        return customer;
    }
    @Override
    public void sendCommunication(Customer customer) {
        try {
            var accountsMsgDto = new AccountMessageDto(customer.getId(), customer.getFirstName(),
                    customer.getEmail(), customer.getMobileNumber());
            log.info("Sending Communication request for the details: {}", accountsMsgDto);
            var result = streamBridge.send("sendCommunication-out-0", accountsMsgDto);
            log.info("Is the Communication request successfully triggered ? : {}", result);
        } catch (Exception exception){
            log.error(exception.getMessage());
        }

    }
    @Override
    public List<CustomerDTO> getCustomers(String correlationId) {
        log.info("getCustomers  correlation-id {}",correlationId);
       List<Customer> customers= customerRepository.findAll();

       List<CustomerDTO> customerDTOS = customers.stream().map(
               customerDataMapper::customerToCustomerDTO)
               .collect(Collectors.toList());
       return  customerDTOS;
    }

    @Override
    public CustomerDTO getCustomerDetails(String mobileNumber) {
       Optional<Customer> customer= customerRepository.findByMobileNumber(mobileNumber);
       if(customer.isEmpty())
       {
           throw new ResourceNotFoundException("Customer","mobile Number",mobileNumber);
       }
       CustomerDTO customerDTO = customerDataMapper.customerToCustomerDTO(customer.get());
return  customerDTO;
    }

    @Override
    public void deleteCustomer(String mobileNumber) {
     Customer customer=customerRepository.findByMobileNumber(mobileNumber).orElseThrow(()->
             new ResourceNotFoundException("Customer","mobilenumber",mobileNumber));
     customerRepository.deleteAllByMobileNumber(mobileNumber);
    }

    @Override
    public boolean updateCustomerCommunication(long id) {
       Customer customer=customerRepository.findById(id).orElseThrow(
               ()->    new ResourceNotFoundException("Customer","Id", Long.toString(id)));
       customer.setCommunication_sw(true);
       customerRepository.save(customer);
       return customer.isCommunication_sw();
    }

}
