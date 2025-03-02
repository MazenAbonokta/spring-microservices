package com.bank.tech.accounts.dto;

import com.bank.tech.accounts.entity.Customer;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddressDTO {
    Long customerId;





    private AddressType addressType;

    private String city;
    private String region;
}
