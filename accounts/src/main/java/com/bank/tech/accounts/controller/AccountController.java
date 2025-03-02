package com.bank.tech.accounts.controller;

import com.bank.tech.accounts.constant.AccountsConstants;
import com.bank.tech.accounts.dto.AccountDTO;
import com.bank.tech.accounts.dto.AccountsContactInfoDto;

import com.bank.tech.accounts.dto.ResponseDTO;
import com.bank.tech.accounts.entity.Account;
import com.bank.tech.accounts.service.CardsFeignClient;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import com.bank.tech.accounts.service.AccountService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController

public class AccountController {
    private static final Logger logger= LoggerFactory.getLogger(AccountController.class);
    @Autowired
    private  AccountService iAccountsService;

    @Autowired
    CardsFeignClient cardsFeignClient;


   /* @Value("${build.version}")*/
    private String buildVersion;

    @Autowired
    private Environment environment;

    @Autowired
    private AccountsContactInfoDto accountsContactInfoDto;


    @PostMapping("/create")
    public ResponseEntity<ResponseDTO> createAccount(@Valid @RequestBody AccountDTO accountDTO) {
        iAccountsService.createAccount(accountDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDTO(AccountsConstants.STATUS_201, AccountsConstants.MESSAGE_201));
    }


    @GetMapping("/fetch")
    public ResponseEntity<Account> fetchAccountDetails(@RequestParam
                                                           @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile number must be 10 digits")
                                                           String mobileNumber) {
        Account account = iAccountsService.getAccountByNumber(mobileNumber);
        return ResponseEntity.status(HttpStatus.OK).body(account);
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseDTO> updateAccountDetails(@Valid @RequestBody AccountDTO Account) {
        boolean isUpdated = iAccountsService.updateAccount(Account);
        if(isUpdated) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDTO(AccountsConstants.STATUS_200, AccountsConstants.MESSAGE_200));
        }else{
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDTO(AccountsConstants.STATUS_417, AccountsConstants.MESSAGE_417_UPDATE));
        }
    }



    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDTO> deleteAccountDetails(@RequestParam
                                                            @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile number must be 10 digits")
                                                            String mobileNumber) {
        boolean isDeleted = iAccountsService.deleteAccount(mobileNumber);
        if(isDeleted) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDTO(AccountsConstants.STATUS_200, AccountsConstants.MESSAGE_200));
        }else{
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDTO(AccountsConstants.STATUS_417, AccountsConstants.MESSAGE_417_DELETE));
        }
    }


    @GetMapping("/build-info")
    public ResponseEntity<String> getBuildInfo() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(buildVersion);
    }


    @RateLimiter(name = "java-version")
    @GetMapping("/java-version")
    public ResponseEntity<String> getJavaVersion() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(environment.getProperty("JAVA_HOME"));
    }
    @RateLimiter(name = "cards-version")
    @GetMapping("/cards-version")

    public String getCards(@RequestHeader("bank-correlation-id")String correlationId) {

      //  logger.info("correlation-id received in  Cards {}", correlationId );

        logger.debug("Start Fetch Card details from account ");
        String info=  cardsFeignClient.fetchinfo(correlationId);
        logger.debug("end Fetch Card details from account ");
        return info;
    }

    @GetMapping("/contact-info")
    public ResponseEntity<AccountsContactInfoDto> getContactInfo() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(accountsContactInfoDto);
    }

    @Retry(name = "getBuildInfoVersion",fallbackMethod = "getBuildInfoVersionFallBack")

    @GetMapping("/getBuildInfoVersion")
    public ResponseEntity<String> getBuildInfoVersion(){
        logger.info("Error-getBuildInfoVersion {}" );
        throw  new RuntimeException();
    }
    public ResponseEntity<String> getBuildInfoVersionFallBack(){
        return  new ResponseEntity<String>("V12.0 Error",HttpStatus.OK);
    }
}
