package com.example.A1LibraryManagement.validator;

import java.sql.Date;
import java.time.LocalDate;

public class BorrowValidator {
    public static boolean isValidDate(Date borrowDate, Date returnDate) {
        boolean status;
        if (returnDate == null){
            status = borrowDate.before(Date.valueOf(LocalDate.now()));
        } else {
            status = borrowDate.before(Date.valueOf(LocalDate.now())) && borrowDate.before(returnDate);
        }
        return  status;
    }
}
