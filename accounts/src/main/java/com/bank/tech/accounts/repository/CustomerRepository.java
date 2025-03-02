package com.bank.tech.accounts.repository;

import com.bank.tech.accounts.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import java.util.Optional;
import java.util.UUID;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {



    Optional<Customer> findByMobileNumber(String mobileNumber);


    void  deleteAllByMobileNumber(String mobileNumber);


}
