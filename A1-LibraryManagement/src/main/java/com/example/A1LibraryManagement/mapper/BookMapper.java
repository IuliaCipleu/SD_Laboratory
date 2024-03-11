package com.example.A1LibraryManagement.mapper;

import com.example.A1LibraryManagement.dto.AuthorDTO;
import com.example.A1LibraryManagement.dto.BookDTO;
import com.example.A1LibraryManagement.dto.BookDTODetails;
import com.example.A1LibraryManagement.model.Author;
import com.example.A1LibraryManagement.model.Book;
import com.example.A1LibraryManagement.repository.AuthorRepository;
import org.springframework.stereotype.Component;

@Component
public class BookMapper {
    private final AuthorRepository authorRepository;

    public BookMapper(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public BookDTO convertToDto(Book book) {
        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setName(book.getAuthor().getName());
        authorDTO.setSurname(book.getAuthor().getSurname());
        return BookDTO.builder()
                .ISBN(book.getISBN())
                .title(book.getTitle())
                .authorDTO(authorDTO)
                .genre(book.getGenre())
                .year(book.getYear())
                .publishingHouse(book.getPublishingHouse())
                .build();
    }

    public BookDTODetails convertToDtoDetails(Book book) {
        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setName(book.getAuthor().getName());
        authorDTO.setSurname(book.getAuthor().getSurname());
        return BookDTODetails.builder()
                .ID(book.getID())
                .ISBN(book.getISBN())
                .title(book.getTitle())
                .authorDTO(authorDTO)
                .genre(book.getGenre())
                .year(book.getYear())
                .publishingHouse(book.getPublishingHouse())
                .copiesInLibrary(book.getCopiesInLibrary())
                .copiesBorrowed(book.getCopiesBorrowed())
                .build();
    }

    public Book convertToEntity(BookDTODetails bookDTO) {
        Book book = new Book();
        book.setISBN(bookDTO.getISBN());
        book.setTitle(bookDTO.getTitle());
        book.setYear(bookDTO.getYear());
        book.setPublishingHouse(bookDTO.getPublishingHouse());
        book.setGenre(bookDTO.getGenre());
        book.setCopiesBorrowed(bookDTO.getCopiesBorrowed());
        book.setCopiesInLibrary(bookDTO.getCopiesInLibrary());
        if (bookDTO.getAuthorDTO() != null) {
            Author existingAuthorBySurname = authorRepository.findBySurname(bookDTO.getAuthorDTO().getSurname())
                    .orElse(null);
            Author existingAuthorByName = authorRepository.findByName(bookDTO.getAuthorDTO().getName())
                    .orElse(null);
            if (existingAuthorBySurname != null) {
                book.setAuthor(existingAuthorBySurname);
            } else if (existingAuthorByName != null) {
                book.setAuthor(existingAuthorByName);
            } else {
                Author newAuthor = new Author();
                newAuthor.setName(bookDTO.getAuthorDTO().getName());
                newAuthor.setSurname(bookDTO.getAuthorDTO().getSurname());
                newAuthor = authorRepository.save(newAuthor);
                book.setAuthor(newAuthor);
            }
        }
        return book;
    }

    public Book convertToEntity(BookDTO bookDTO) {
        Book book = new Book();
        book.setISBN(bookDTO.getISBN());
        book.setTitle(bookDTO.getTitle());
        book.setYear(bookDTO.getYear());
        book.setPublishingHouse(bookDTO.getPublishingHouse());
        book.setGenre(bookDTO.getGenre());
        if (bookDTO.getAuthorDTO() != null) {
            Author existingAuthorBySurname = authorRepository.findBySurname(bookDTO.getAuthorDTO().getSurname())
                    .orElse(null);
            Author existingAuthorByName = authorRepository.findByName(bookDTO.getAuthorDTO().getName())
                    .orElse(null);
            if (existingAuthorBySurname != null) {
                book.setAuthor(existingAuthorBySurname);
            } else if (existingAuthorByName != null) {
                book.setAuthor(existingAuthorByName);
            } else {
                Author newAuthor = new Author();
                newAuthor.setName(bookDTO.getAuthorDTO().getName());
                newAuthor.setSurname(bookDTO.getAuthorDTO().getSurname());
                newAuthor = authorRepository.save(newAuthor);
                book.setAuthor(newAuthor);
            }
        }
        return book;
    }
}
