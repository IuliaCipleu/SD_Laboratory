package com.example.A1LibraryManagement.dto;

import com.example.A1LibraryManagement.model.Genre;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO {
    private String ISBN;
    private String title;
    private int year;
    private AuthorDTO authorDTO;
    private String publishingHouse;
    private Genre genre;
}
