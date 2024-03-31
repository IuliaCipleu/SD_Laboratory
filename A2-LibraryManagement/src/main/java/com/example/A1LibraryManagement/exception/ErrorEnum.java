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
    INVALID_RETURN_DATE("Invalid return date"),
    WRONG_PASSWORD("Wrong Password"),
    INVALID_PASSWORD("Invalid password! Password must have at least 8 characters, must contain both lowercase and uppercase characters and must contain at least one special character (!@#$%^&*?"),
    INVALID_NAME("Invalid name"),
    USER_ALREADY_EXISTS("User already exist"),
    USER_NOT_FOUND ("User not found"),
    ACCESS_DENIED ("Access denied: user must login first!");


    private String message;

    ErrorEnum(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}