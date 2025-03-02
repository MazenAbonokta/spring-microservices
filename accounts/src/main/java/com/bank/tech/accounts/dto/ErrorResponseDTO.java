package com.bank.tech.accounts.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@Data
public class ErrorResponseDTO {
    private String apiPath;
    private HttpStatus statusCode;
    private String message;
    private LocalDateTime errorTime;
}
