package com.example.A1LibraryManagement.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthorDTO {
    private UUID ID;
    private String surname;
    private String name;
}
