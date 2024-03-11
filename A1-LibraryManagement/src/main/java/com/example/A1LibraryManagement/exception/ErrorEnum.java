package com.example.A1LibraryManagement.exception;

public enum ErrorEnum {
    AUTHOR_NOT_FOUND("Author not found"),
    BOOK_NOT_FOUND("Book not found"),
    BORROW_NOT_FOUND("Borrow not found"),
    BORROWER_NOT_FOUND("Borrower not found"),
    INVALID_DATE_OF_BIRTH("Invalid date of birth"),
    INVALID_EMAIL("Invalid email"),
    INVALID_YEAR("Invalid year"),
    NEGATIVE_NUMBER("Negative number"),
    INVALID_RETURN_DATE("Invalid return date");


    private String message;

    ErrorEnum(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}