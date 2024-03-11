package com.example.A1LibraryManagement.dto;

import com.example.A1LibraryManagement.model.Genre;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookDTODetails {
    private UUID ID;
    private String ISBN;
    private String title;
    private int year;
    private AuthorDTO authorDTO;
    private String publishingHouse;
    private int copiesBorrowed;
    private int copiesInLibrary;
    private Genre genre;
}
