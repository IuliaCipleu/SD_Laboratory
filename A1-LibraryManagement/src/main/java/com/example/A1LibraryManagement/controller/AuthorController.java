package com.example.A1LibraryManagement.controller;

import com.example.A1LibraryManagement.dto.AuthorDTO;
import com.example.A1LibraryManagement.mapper.AuthorMapper;
import com.example.A1LibraryManagement.model.Author;
import com.example.A1LibraryManagement.repository.AuthorRepository;
import com.example.A1LibraryManagement.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        return authorRepository.findAll();
    }

    @GetMapping("/")
    public List<AuthorDTO> getAllAuthors() {
        List<Author> authors = authorRepository.findAll();
        return authors.stream()
                .map(authorMapper::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/ID/{id}")
    public ResponseEntity<AuthorDTO> getAuthorById(@PathVariable UUID id) {
        return ResponseEntity.ok(authorService.getAuthorById(id));
    }

    @GetMapping("/surname/{surname}")
    public ResponseEntity<AuthorDTO> getAuthorBySurname(@PathVariable String surname) {
        return ResponseEntity.ok(authorService.getAuthorBySurname(surname));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<AuthorDTO> getAuthorByName(@PathVariable String name) {
        return ResponseEntity.ok(authorService.getAuthorByName(name));
    }

    @PostMapping("/")
    public AuthorDTO createAuthor(@RequestBody AuthorDTO authorDTO) {
        return authorService.createAuthor(authorDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAuthor(@PathVariable UUID id) {
        try {
            authorService.deleteAuthorByID(id);
            return ResponseEntity.ok("Author deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error deleting author: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateAuthor(@PathVariable UUID id, @RequestBody AuthorDTO updatedAuthor) {
        try {
            authorService.updateAuthor(id, updatedAuthor);
            return ResponseEntity.ok("Author updated successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error updating author: " + e.getMessage());
        }
    }
}

