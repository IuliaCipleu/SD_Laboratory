package com.example.A1LibraryManagement.exception;

public class ApiException extends RuntimeException {
    private ErrorEnum error;

    public ApiException(ErrorEnum error) {
        super(error.getMessage());
        this.error = error;
    }

    public ErrorEnum getError() {
        return error;
    }
}
