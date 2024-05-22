package com.example.A1LibraryManagement.controller;

import com.example.A1LibraryManagement.config.CheckAuthentication;
import com.example.A1LibraryManagement.dto.BookDTO;
import com.example.A1LibraryManagement.dto.BookDTODetails;
import com.example.A1LibraryManagement.model.Book;
import com.example.A1LibraryManagement.repository.BookRepository;
import com.example.A1LibraryManagement.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;
    @Autowired
    private BookRepository bookRepository;
    private final CheckAuthentication checkAuthentication;

//    public BookController(BookService bookService, BookRepository bookRepository, CheckAuthentication checkAuthentication) {
//        this.bookService = bookService;
//        this.bookRepository = bookRepository;
//        this.checkAuthentication = checkAuthentication;
//    }

    @GetMapping("/")
    public List<Book> getAllBooks() {
        //CheckAuthentication checkAuthentication = new CheckAuthentication();
        //checkAuthentication.checkAuthenticationAll();
        return bookRepository.findAll();
    }

    @GetMapping("/ID/{id}")
    public ResponseEntity<BookDTO> getBookByID(@PathVariable UUID id) {
        //CheckAuthentication checkAuthentication = new CheckAuthentication();
        checkAuthentication.checkAuthenticationAll();
        return ResponseEntity.ok(bookService.getBookById(id));
    }

    @GetMapping("/ISBN/{isbn}")
    public ResponseEntity<BookDTODetails> getBookByISBN(@PathVariable String isbn) {
        //CheckAuthentication checkAuthentication = new CheckAuthentication();
        checkAuthentication.checkAuthenticationAll();
        return ResponseEntity.ok(bookService.getBookByISBN(isbn));
    }

    @GetMapping("/title/{title}")
    public ResponseEntity<BookDTO> getBookByTitle(@PathVariable String title) {
        //CheckAuthentication checkAuthentication = new CheckAuthentication();
        checkAuthentication.checkAuthenticationAll();
        return ResponseEntity.ok(bookService.getBookByTitle(title));
    }

    @PostMapping("/")
    public BookDTODetails createBook(@RequestBody BookDTODetails bookDTODetails) {
        //CheckAuthentication checkAuthentication = new CheckAuthentication();
        checkAuthentication.checkAuthenticationRole("ADMIN");
        return bookService.createBook(bookDTODetails);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteBook(@PathVariable UUID id) {
        //CheckAuthentication checkAuthentication = new CheckAuthentication();
        checkAuthentication.checkAuthenticationRole("ADMIN");
        try {
            bookService.deleteBookByID(id);
            return ResponseEntity.ok().body(Collections.singletonMap("message", "Book deleted successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("error", "Error deleting book: " + e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateBook(@PathVariable UUID id, @RequestBody BookDTODetails updatedBook) {
        //CheckAuthentication checkAuthentication = new CheckAuthentication();
        checkAuthentication.checkAuthenticationRole("ADMIN");
        try {
            bookService.updateBook(id, updatedBook);
            return ResponseEntity.ok().body(Collections.singletonMap("message", "Book updated successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("error", "Error updating book: " + e.getMessage()));
        }
    }
}