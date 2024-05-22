package com.example.A1LibraryManagement.service;

import com.example.A1LibraryManagement.dto.AuthorDTO;
import com.example.A1LibraryManagement.exception.ApiException;
import com.example.A1LibraryManagement.exception.ErrorEnum;
import com.example.A1LibraryManagement.mapper.AuthorMapper;
import com.example.A1LibraryManagement.model.Author;
import com.example.A1LibraryManagement.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthorService {
    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;

    public List<AuthorDTO> getAll() {
        List<Author> authors = authorRepository.findAll();
        if (authors.isEmpty()) {
            throw new ApiException(ErrorEnum.AUTHOR_NOT_FOUND);
        }
        return authors.stream()
                .map(authorMapper::convertToDto)
                .collect(Collectors.toList());
    }

    public AuthorDTO getAuthorById(UUID id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new ApiException(ErrorEnum.AUTHOR_NOT_FOUND));
        return authorMapper.convertToDto(author);
    }

    public AuthorDTO getAuthorBySurname(String surname) {
        final Author author = authorRepository.findBySurname(surname)
                .orElseThrow(() -> new ApiException(ErrorEnum.AUTHOR_NOT_FOUND));
        return authorMapper.convertToDto(author);
    }

    public AuthorDTO getAuthorByName(String name) {
        final Author author = authorRepository.findByName(name)
                .orElseThrow(() -> new ApiException(ErrorEnum.AUTHOR_NOT_FOUND));
        return authorMapper.convertToDto(author);
    }

    public AuthorDTO createAuthor(AuthorDTO authorDTO) {
        Author author = authorMapper.convertToEntity(authorDTO);
        author = authorRepository.save(author);
        return authorMapper.convertToDto(author);
    }

    public void deleteAuthorByID(UUID ID) {
        final Author author = authorRepository.findById(ID)
                .orElseThrow(() -> new ApiException(ErrorEnum.AUTHOR_NOT_FOUND));
        authorRepository.deleteById(ID);
    }

    public void updateAuthor(UUID ID, AuthorDTO updatedAuthor){
        final Author author = authorRepository.findById(ID)
                .orElseThrow(() -> new ApiException(ErrorEnum.AUTHOR_NOT_FOUND));
        author.setName(updatedAuthor.getName());
        author.setSurname(updatedAuthor.getSurname());
        authorRepository.save(author);
    }
}
