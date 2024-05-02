package com.example.A1LibraryManagement.service;

import com.example.A1LibraryManagement.dto.AuthorDTO;
import com.example.A1LibraryManagement.dto.BookDTO;
import com.example.A1LibraryManagement.dto.BookDTODetails;
import com.example.A1LibraryManagement.exception.ApiException;
import com.example.A1LibraryManagement.exception.ErrorEnum;
import com.example.A1LibraryManagement.mapper.AuthorMapper;
import com.example.A1LibraryManagement.mapper.BookMapper;
import com.example.A1LibraryManagement.model.Author;
import com.example.A1LibraryManagement.model.Book;
import com.example.A1LibraryManagement.repository.AuthorRepository;
import com.example.A1LibraryManagement.repository.BookRepository;
import com.example.A1LibraryManagement.validator.BookValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final AuthorMapper authorMapper;
    private final AuthorRepository authorRepository;

    public BookDTO getBookById(UUID id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ApiException(ErrorEnum.BOOK_NOT_FOUND));
        return bookMapper.convertToDto(book);
    }

    public BookDTODetails getBookByISBN(String ISBN) {
        final Book book = (Book) bookRepository.findByISBN(String.valueOf(ISBN))
                .orElseThrow(() -> new ApiException(ErrorEnum.BOOK_NOT_FOUND));
        return bookMapper.convertToDtoDetails(book);
    }



    public BookDTO getBookByTitle(String title) {
        final Book book = bookRepository.findByTitle(title)
                .orElseThrow(() -> new ApiException(ErrorEnum.BOOK_NOT_FOUND));
        return bookMapper.convertToDto(book);
    }

    public BookDTODetails createBook(BookDTODetails bookDTO) {
        if(!BookValidator.isValidYear(bookDTO.getYear())){
            throw new ApiException(ErrorEnum.INVALID_YEAR);
        }
        if(!BookValidator.isNumberPositive(bookDTO.getCopiesBorrowed())){
            throw new ApiException(ErrorEnum.NEGATIVE_NUMBER);
        }
        if(!BookValidator.isNumberPositive(bookDTO.getCopiesInLibrary())){
            throw new ApiException(ErrorEnum.NEGATIVE_NUMBER);
        }
        Book book = bookMapper.convertToEntity(bookDTO);
        book = bookRepository.save(book);
        return bookMapper.convertToDtoDetails(book);
    }

    public void deleteBookByID(UUID ID) {
        final Book book = bookRepository.findById(ID)
                .orElseThrow(() -> new ApiException(ErrorEnum.BOOK_NOT_FOUND));
        bookRepository.deleteById(ID);
    }

    public void updateBook(UUID ID, BookDTODetails updatedBook) {
        if(!BookValidator.isValidYear(updatedBook.getYear())){
            throw new ApiException(ErrorEnum.INVALID_YEAR);
        }
        if(!BookValidator.isNumberPositive(updatedBook.getCopiesBorrowed())){
            throw new ApiException(ErrorEnum.NEGATIVE_NUMBER);
        }
        if(!BookValidator.isNumberPositive(updatedBook.getCopiesInLibrary())){
            throw new ApiException(ErrorEnum.NEGATIVE_NUMBER);
        }
        final Book book = bookRepository.findById(ID)
                .orElseThrow(() -> new ApiException(ErrorEnum.BOOK_NOT_FOUND));
        book.setCopiesBorrowed(updatedBook.getCopiesBorrowed());
        book.setCopiesInLibrary(updatedBook.getCopiesInLibrary());
        book.setGenre(updatedBook.getGenre());
        book.setPublishingHouse(updatedBook.getPublishingHouse());
        book.setTitle(updatedBook.getTitle());
        book.setYear(updatedBook.getYear());
        bookRepository.save(book);
    }
}
