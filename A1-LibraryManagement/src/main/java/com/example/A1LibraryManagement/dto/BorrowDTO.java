package com.example.A1LibraryManagement.dto;

import com.example.A1LibraryManagement.model.Borrower;
import lombok.*;

import java.sql.Date;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BorrowDTO {
    //private BookDTO bookDTO;
    //private Borrower borrower;
    private String bookISBN;
    private UUID borrowerID;
    private Date borrowDate;
    private Date returnDate;
}
