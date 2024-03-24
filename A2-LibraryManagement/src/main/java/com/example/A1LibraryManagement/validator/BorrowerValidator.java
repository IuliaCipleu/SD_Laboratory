package com.example.A1LibraryManagement.validator;

import java.time.LocalDate;
import java.util.regex.Pattern;

public class BorrowerValidator {
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
    public static boolean isValidDateOfBirth(LocalDate dateOfBirth) {
        return !dateOfBirth.isAfter(LocalDate.now());
    }

    public static boolean isValidEmail(String email) {
        return EMAIL_PATTERN.matcher(email).matches();
    }
}
