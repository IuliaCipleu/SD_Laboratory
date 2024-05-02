package com.example.A1LibraryManagement.validator;

import java.time.LocalDate;

public class BookValidator {
    public static boolean isValidYear(int year) {
        return (year <= LocalDate.now().getYear());
    }
    public static boolean isNumberPositive(int nr){
        return (nr >= 0);
    }
}
