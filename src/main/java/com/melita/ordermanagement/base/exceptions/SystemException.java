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
public class SystemException extends Exception {

    private String    message;
    private Throwable cause;

}
