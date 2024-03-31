package com.example.A1LibraryManagement.validator;

import java.time.LocalDate;
import java.util.regex.Pattern;

public class UserValidator {
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
    public static boolean isValidDateOfBirth(LocalDate dateOfBirth) {
        return !dateOfBirth.isAfter(LocalDate.now());
    }

    public static boolean isOnlyLetters(String name) {
        for (int i = 0; i < name.length(); i++) {
            char c = name.charAt(i);
            if (!((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z'))) {
                System.out.println(c);
                return false;
            }
        }
        return true;
    }

    public static boolean isPasswordValid(String password){
        if (password.length() < 8){
            System.out.println(password.length());
            return false;
        }
        if (!password.matches(".*[!@#$%^&*?].*")){
            System.out.println("Special character missing");
            return false;
        }
        if (password.toLowerCase().equals(password)){
            System.out.println("No Uppercase");
            return false;
        }
        return !password.toUpperCase().equals(password);
    }

    public static boolean isValidEmail(String email) {
        return EMAIL_PATTERN.matcher(email).matches();
    }
}
