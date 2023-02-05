package com.melita.ordermanagement.api.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/*
 * @author sorokus.dev@gmail.com
 */

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
class BusinessError extends ApiSubError {

    private String message;

}