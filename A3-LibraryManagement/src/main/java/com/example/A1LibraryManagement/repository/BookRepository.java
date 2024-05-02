package com.example.A1LibraryManagement.repository;

import com.example.A1LibraryManagement.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface BookRepository extends JpaRepository<Book, UUID> {
    Optional<Book> findByTitle(String title);

    Optional<Book> findByISBN(String isbn);
}

