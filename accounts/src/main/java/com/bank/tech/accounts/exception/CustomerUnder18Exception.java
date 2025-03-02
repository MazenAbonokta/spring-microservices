package com.bank.tech.accounts.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class CustomerUnder18Exception extends RuntimeException{


    public  CustomerUnder18Exception(String message){
        super(message);
    }
}
