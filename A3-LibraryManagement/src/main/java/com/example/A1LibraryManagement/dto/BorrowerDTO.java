package com.example.A1LibraryManagement.dto;

import lombok.*;

import java.sql.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BorrowerDTO {
    //private UUID ID;
    private String name;
    private String surname;
    private Date dateOfBirth;
    private String email;
}
