package com.example.A1LibraryManagement.controller;

import com.example.A1LibraryManagement.config.CheckAuthentication;
import com.example.A1LibraryManagement.dto.AuthorDTO;
import com.example.A1LibraryManagement.mapper.AuthorMapper;
import com.example.A1LibraryManagement.model.Author;
import com.example.A1LibraryManagement.repository.AuthorRepository;
import com.example.A1LibraryManagement.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/authors")
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private AuthorMapper authorMapper;

    @GetMapping("/allDetails/")
    public List<Author> getAllUAuthors() {

        CheckAuthentication checkAuthentication = new CheckAuthentication();
        checkAuthentication.checkAuthenticationAll();
        return authorRepository.findAll();
    }

    @GetMapping("/")
    public List<AuthorDTO> getAllAuthors() {
        CheckAuthentication checkAuthentication = new CheckAuthentication();
        checkAuthentication.checkAuthenticationAll();
        List<Author> authors = authorRepository.findAll();
        return authors.stream()
                .map(authorMapper::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/ID/{id}")
    public ResponseEntity<AuthorDTO> getAuthorById(@PathVariable UUID id) {
        CheckAuthentication checkAuthentication = new CheckAuthentication();
        checkAuthentication.checkAuthenticationAll();
        return ResponseEntity.ok(authorService.getAuthorById(id));
    }

    @GetMapping("/surname/{surname}")
    public ResponseEntity<AuthorDTO> getAuthorBySurname(@PathVariable String surname) {
        CheckAuthentication checkAuthentication = new CheckAuthentication();
        checkAuthentication.checkAuthenticationAll();
        return ResponseEntity.ok(authorService.getAuthorBySurname(surname));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<AuthorDTO> getAuthorByName(@PathVariable String name) {
        CheckAuthentication checkAuthentication = new CheckAuthentication();
        checkAuthentication.checkAuthenticationAll();
        return ResponseEntity.ok(authorService.getAuthorByName(name));
    }

    @PostMapping("/")
    public AuthorDTO createAuthor(@RequestBody AuthorDTO authorDTO) {
        CheckAuthentication checkAuthentication = new CheckAuthentication();
        checkAuthentication.checkAuthenticationRole("ADMIN");
        return authorService.createAuthor(authorDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteAuthor(@PathVariable UUID id) {
        CheckAuthentication checkAuthentication = new CheckAuthentication();
        checkAuthentication.checkAuthenticationRole("ADMIN");
        try {
            authorService.deleteAuthorByID(id);
            return ResponseEntity.ok().body(Collections.singletonMap("message", "Author deleted successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("error", "Error deleting author: " + e.getMessage()));
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<Object> updateAuthor(@PathVariable UUID id, @RequestBody AuthorDTO updatedAuthor) {
        CheckAuthentication checkAuthentication = new CheckAuthentication();
        checkAuthentication.checkAuthenticationRole("ADMIN");
        try {
            authorService.updateAuthor(id, updatedAuthor);
            return ResponseEntity.ok().body(Collections.singletonMap("message", "Author updated successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("error", "Error updating author: " + e.getMessage()));
        }
    }
}

