package com.bank.tech.accounts.service;

import com.bank.tech.accounts.imp.CardsFeignClientImp;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "cards",fallback = CardsFeignClientImp.class)
public interface CardsFeignClient {
    @GetMapping("/api/fetchinfo")
    public String fetchinfo (@RequestHeader("bank-correlation-id") String correlationId);



}
