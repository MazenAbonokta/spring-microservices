package com.bank.tech.accounts.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerRequest {
    @NotEmpty
    @Size(min = 2,max = 10,message = "firstName  should be 9 or 10 ")
    private String firstName;
    @NotEmpty
    private String lastName;
    @Email(message = "email should be valid value")
    private String email;
    private LocalDate dateOfBrith;

    @Pattern(regexp = ("^$|[0-9]{10}" ),message = "mobile number should be 10 digits")
    private String mobileNumber;
}
