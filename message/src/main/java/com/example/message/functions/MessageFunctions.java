package com.example.message.functions;

import com.example.message.Interface.IEmailService;
import com.example.message.dto.AccountMessageDto;
import com.example.message.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;


@Configuration

public class MessageFunctions {

    private static final Logger logger = LoggerFactory.getLogger(MessageFunctions.class);
    @Autowired
    private IEmailService emailService;
    @Bean
    public Function<AccountMessageDto, AccountMessageDto> email(){
        return accountMessageDto -> {
            logger.info("Sending Email to :" ,accountMessageDto.accountNumber());
            String Body="The Customer with Account number "+ accountMessageDto.accountNumber()+" has been added";
          //  emailService.sendEmail(accountMessageDto.email(),"Add New Customer",Body);
            logger.info("Email has been send:" ,accountMessageDto.email());
        return accountMessageDto;
        };
    }
    @Bean
    public Function<AccountMessageDto, Long> sms(){
        return accountMessageDto -> {
            logger.info("Sending SMS to :" ,accountMessageDto.accountNumber());
            return accountMessageDto.accountNumber();
        };
    }
}
