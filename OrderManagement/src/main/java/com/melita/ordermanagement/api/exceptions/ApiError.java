package com.melita.ordermanagement.api.exceptions;
/*
 * @author sorokus.dev@gmail.com
 */

import com.fasterxml.jackson.annotation.JsonFormat;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
class ApiError {

    private HttpStatus        status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime     timestamp;
    private String            message;
    private String            debugMessage;
    private List<ApiSubError> subErrors;

    private ApiError() {
        timestamp = LocalDateTime.now();
    }

    ApiError(HttpStatus status, String message, Throwable ex) {
        this();
        this.status = status;
        this.message = message;
        this.debugMessage = ex.getLocalizedMessage();
    }

    ApiError(HttpStatus status, String message, String debugMessage, List<ApiSubError> subErrors) {
        this();
        this.status = status;
        this.message = message;
        this.debugMessage = debugMessage;
        this.subErrors = subErrors;
    }

}