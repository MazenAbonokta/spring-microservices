package com.bank.tech.accounts.dto;
import java.time.Period;

import com.bank.tech.accounts.exception.CustomerUnder18Exception;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

import static com.bank.tech.accounts.constant.AccountsConstants.RETIREMENT_AGE;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDTO {
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

   private List<AccountDTO> accounts;
    public  void validateDateOfBrith()
    {
        LocalDate today = LocalDate.now();
        Period age = Period.between(dateOfBrith, today);
       if(age.getYears()<RETIREMENT_AGE){
           throw new CustomerUnder18Exception("You cannot add accounts because you are under  18");
       }
    }

}
