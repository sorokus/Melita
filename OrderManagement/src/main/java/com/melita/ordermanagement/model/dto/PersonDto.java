package com.melita.ordermanagement.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

/*
 * @author sorokus.dev@gmail.com
 */

@Data
public class PersonDto {
    @NotEmpty(message = "Please provide a 'firstName'")
    private String firstName;

    @NotEmpty(message = "Please provide a 'lastName'")
    private String lastName;

    @NotEmpty(message = "Please provide a 'mobileNo'")
    private String mobileNo;

    // Validation rule could be adjusted based on edge cases
    @Email(regexp = "^(?=.{1,64}@)[A-Za-z0-9\\+_-]+(\\.[A-Za-z0-9\\+_-]+)*@" +
            "[^-][A-Za-z0-9\\+-]+(\\.[A-Za-z0-9\\+-]+)*(\\.[A-Za-z]{2,})$",
           message = "Email is invalid. Please provide a correct one.")
    @NotEmpty(message = "Please provide an 'email'")
    private String email;

    @NotEmpty(message = "Please provide a 'passportNo'")
    private String passportNo;
}
