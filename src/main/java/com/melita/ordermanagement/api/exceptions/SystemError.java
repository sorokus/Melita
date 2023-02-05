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
class SystemError extends ApiSubError {

    private String message;
    private Throwable cause;
}