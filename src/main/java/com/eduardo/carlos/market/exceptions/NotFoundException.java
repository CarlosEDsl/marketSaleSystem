package com.eduardo.carlos.market.exceptions;

import org.springframework.http.HttpStatus;

public class NotFoundException extends RuntimeException {
    private HttpStatus httpStatus;
    private int status;

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, HttpStatus httpStatus, int status) {
        super(message);
        this.httpStatus = httpStatus;
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
