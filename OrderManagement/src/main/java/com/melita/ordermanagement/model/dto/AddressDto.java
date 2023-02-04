package com.melita.ordermanagement.model.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

/*
 * @author sorokus.dev@gmail.com
 */

@Data
public class AddressDto {

    @NotEmpty(message = "Please provide a 'province'")
    private String province;

    @NotEmpty(message = "Please provide a 'city'")
    private String city;

    @NotEmpty(message = "Please provide a 'street'")
    private String street;

    @NotEmpty(message = "Please provide an 'apartment'")
    private String apartment;

    @NotEmpty(message = "Please provide a 'postalCode'")
    private String postalCode;
}
