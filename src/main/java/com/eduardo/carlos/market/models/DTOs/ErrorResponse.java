package com.eduardo.carlos.market.models.DTOs;

import org.springframework.http.HttpStatus;

public class ErrorResponse {
    private String message;
    private HttpStatus httpStatus;
    private int status;

    public ErrorResponse(String message, HttpStatus errorCode, int status) {
        this.message = message;
        this.httpStatus = errorCode;
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
