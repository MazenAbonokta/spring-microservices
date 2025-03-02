package com.bank.tech.accounts.imp;

import com.bank.tech.accounts.service.CardsFeignClient;
import org.springframework.stereotype.Component;

@Component
public class CardsFeignClientImp implements CardsFeignClient {
    @Override
    public String fetchinfo(String correlationId) {
        return "FallBack WithCards Version with correlation-id" + correlationId ;
    }
}
