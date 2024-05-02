package com.example.A1LibraryManagement.dto;

import lombok.*;

import java.sql.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BorrowerDTODetails {
    private String name;
    private String surname;
    private Date dateOfBirth;
    private String email;
    //private String password;
}
