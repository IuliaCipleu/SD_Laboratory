package com.example.A1LibraryManagement.mapper;

import com.example.A1LibraryManagement.dto.AuthorDTO;
import com.example.A1LibraryManagement.model.Author;
import org.springframework.stereotype.Component;

@Component
public class AuthorMapper {
    public AuthorDTO convertToDto(Author author) {
        return AuthorDTO.builder()
                .ID(author.getID())
                .surname(author.getSurname())
                .name(author.getName())
                .build();
    }

    public Author convertToEntity(AuthorDTO authorDTO) {
        Author author = new Author();
        author.setSurname(authorDTO.getSurname());
        author.setName(authorDTO.getName());
        return author;
    }
}