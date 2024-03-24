package com.example.A1LibraryManagement.repository;

import com.example.A1LibraryManagement.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AuthorRepository extends JpaRepository<Author, UUID> {
    Optional<Author> findBySurname(String surname);

    Optional<Author> findByName(String name);

    Optional<Author> findById(UUID id);

    void deleteById(UUID id);
}