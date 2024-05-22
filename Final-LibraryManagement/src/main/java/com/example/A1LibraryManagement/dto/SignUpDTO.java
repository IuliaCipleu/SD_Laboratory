package com.example.A1LibraryManagement.dto;

import lombok.Data;

import java.sql.Date;

@Data
public class SignUpDTO {
    private String name;
    private String surname;
    private String username;
    private String email;
    private String password;
    private Date dateOfBirth;
}