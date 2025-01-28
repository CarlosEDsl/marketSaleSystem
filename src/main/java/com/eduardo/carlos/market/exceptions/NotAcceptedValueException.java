package com.eduardo.carlos.market.exceptions;

import org.springframework.http.HttpStatus;

public class NotAcceptedValueException extends RuntimeException {

    private HttpStatus httpStatus;
    private int status;

    public NotAcceptedValueException(String message) {
        super(message);
        this.httpStatus = HttpStatus.BAD_REQUEST;
        this.status = 400;
    }

    public int getStatus() {
        return status;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

}
