package com.bank.tech.accounts.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "loans")
public interface LoansFeignClient {
    @GetMapping("/fetchinfo")
    public ResponseEntity<String> fetchinfo ();
}
