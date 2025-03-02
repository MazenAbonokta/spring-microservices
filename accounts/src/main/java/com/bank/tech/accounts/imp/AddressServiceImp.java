package com.bank.tech.accounts.imp;

import com.bank.tech.accounts.dto.AddressDTO;
import com.bank.tech.accounts.entity.Address;
import com.bank.tech.accounts.entity.Customer;
import com.bank.tech.accounts.mapper.CustomerDataMapper;
import com.bank.tech.accounts.repository.AddressRepository;
import com.bank.tech.accounts.service.AddressService;
import com.bank.tech.accounts.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressServiceImp implements AddressService {
    @Autowired
    AddressRepository addressRepository;
    @Autowired
    CustomerService customerService;
    @Autowired
    CustomerDataMapper customerDataMapper;
    @Override
    public void createAddress(AddressDTO addressDTO) {
        Customer customer=customerService.getCustomerById(addressDTO.getCustomerId());
        Address address= customerDataMapper.addressDTOToAddressEntity(addressDTO,customer);
        addressRepository.save(address);
    }

}
