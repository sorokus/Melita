package com.melita.ordermanagement.base.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/*
 * @author sorokus.dev@gmail.com
 */

@Getter
@Setter
@AllArgsConstructor
public class BusinessException extends Exception {

    private String    message;
    private Throwable exc;

    public BusinessException(String message) {
        this.message = message;
    }
}
