package com.melita.ordermanagement.model.dto;

import java.util.Date;
import java.util.Set;

import jakarta.validation.Valid;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

/*
 * @author sorokus.dev@gmail.com
 */

@Data
public class OrderDto {

    @NotNull(message = "Please provide at least 1 'packageIds'")
    @Size(min = 1,
          message = "Please provide at least 1 'packageIds'")
    private Set<Long> packageIds;

    @Valid
    @NotNull(message = "Please provide 'address' info")
    private AddressDto address;

    @NotNull(message = "Please provide 'person' info")
    @Valid
    private PersonDto person;

    @NotNull(message = "Please provide a 'prefDateFrom'")
    @FutureOrPresent
    private Date prefDateFrom;

    @NotNull(message = "Please provide a 'prefDateTo'")
    @FutureOrPresent
    private Date prefDateTo;

    private Date   submittedAt = new Date();
    private Date   approvedAt;
    private String approvedBy;
}
