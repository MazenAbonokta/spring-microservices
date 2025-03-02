package com.bank.tech.accounts.controller;

import com.bank.tech.accounts.dto.AddressDTO;
import com.bank.tech.accounts.dto.CustomerDTO;
import com.bank.tech.accounts.dto.ResponseDTO;
import com.bank.tech.accounts.entity.Address;
import com.bank.tech.accounts.entity.Customer;
import com.bank.tech.accounts.service.AddressService;
import com.bank.tech.accounts.service.CustomerService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/customer")
@Validated
public class CustomerController {
    private static final Logger logger= LoggerFactory.getLogger(CustomerController.class);
    @Autowired
    CustomerService customerService;
    @Autowired
    AddressService addressService;

    @PostMapping("/create")
    public ResponseEntity CreateCustomer (@Valid @RequestBody CustomerDTO customerDTO){
        Customer customer =customerService.createCustomer(customerDTO);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDTO(HttpStatus.CREATED.toString(),"the customer has been created"));
    }
    @PostMapping("/address/create/")
    public ResponseEntity CreateAddress (@RequestBody AddressDTO address){
       addressService.createAddress(address);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDTO(HttpStatus.CREATED.toString(),"the address has been created"));
    }

    @GetMapping("/fetch")
    public ResponseEntity<CustomerDTO> fetchCustomerDetails(

            @RequestParam
                       @Pattern(regexp = ("^$|[0-9]{10}" ),message = "mobile number should be 10 digits")
                        String mobileNumber){
        return null;

    }
    @GetMapping("/list")
    public ResponseEntity<List<CustomerDTO>> fetchCustomerList( @RequestHeader("bank-correlation-id")String correlationId){
        logger.info("correlation-id received in  Cards {}", correlationId );
        return  ResponseEntity
                .status(HttpStatus.OK)
                .body(customerService.getCustomers(correlationId));

    }
    @DeleteMapping("/delete/{mobileNumber}")
    public ResponseEntity<String> deleteCustomer(@PathVariable String mobileNumber){
        customerService.deleteCustomer(mobileNumber);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("The Customer with Mobile number " + mobileNumber + " has been deleted");
    }
}
