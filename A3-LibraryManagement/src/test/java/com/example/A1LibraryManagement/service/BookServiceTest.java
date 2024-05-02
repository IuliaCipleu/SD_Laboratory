package com.example.A1LibraryManagement.service;

import com.example.A1LibraryManagement.dto.BookDTO;
import com.example.A1LibraryManagement.dto.BookDTODetails;
import com.example.A1LibraryManagement.exception.ApiException;
import com.example.A1LibraryManagement.mapper.BookMapper;
import com.example.A1LibraryManagement.model.Book;
import com.example.A1LibraryManagement.model.Genre;
import com.example.A1LibraryManagement.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookMapper bookMapper;

    @InjectMocks
    private BookService bookService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getBookById_BookFound_ShouldReturnBookDTO() {
        // Arrange
        UUID id = UUID.randomUUID();
        Book book = new Book();
        BookDTO expectedDTO = new BookDTO();
        when(bookRepository.findById(id)).thenReturn(Optional.of(book));
        when(bookMapper.convertToDto(book)).thenReturn(expectedDTO);

        // Act
        BookDTO actualDTO = bookService.getBookById(id);

        // Assert
        assertEquals(expectedDTO, actualDTO);
    }

    @Test
    void getBookById_BookNotFound_ShouldThrowApiException() {
        // Arrange
        UUID id = UUID.randomUUID();
        when(bookRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ApiException.class, () -> bookService.getBookById(id));
    }

    @Test
    void getBookByISBN_BookFound_ShouldReturnBookDTODetails() {
        // Arrange
        String isbn = "1234567890";
        Book book = new Book();
        BookDTODetails expectedDTO = new BookDTODetails();
        when(bookRepository.findByISBN(isbn)).thenReturn(Optional.of(book));
        when(bookMapper.convertToDtoDetails(book)).thenReturn(expectedDTO);

        // Act
        BookDTODetails actualDTO = bookService.getBookByISBN(isbn);

        // Assert
        assertEquals(expectedDTO, actualDTO);
    }

    @Test
    void getBookByISBN_BookNotFound_ShouldThrowApiException() {
        // Arrange
        String isbn = "1234567890";
        when(bookRepository.findByISBN(isbn)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ApiException.class, () -> bookService.getBookByISBN(isbn));
    }

    @Test
    void getBookByTitle_BookFound_ShouldReturnBookDTO() {
        // Arrange
        String title = "Sample Title";
        Book book = new Book();
        BookDTO expectedDTO = new BookDTO();
        when(bookRepository.findByTitle(title)).thenReturn(Optional.of(book));
        when(bookMapper.convertToDto(book)).thenReturn(expectedDTO);

        // Act
        BookDTO actualDTO = bookService.getBookByTitle(title);

        // Assert
        assertEquals(expectedDTO, actualDTO);
    }

    @Test
    void getBookByTitle_BookNotFound_ShouldThrowApiException() {
        // Arrange
        String title = "Sample Title";
        when(bookRepository.findByTitle(title)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ApiException.class, () -> bookService.getBookByTitle(title));
    }

    @Test
    void createBook_ValidBookDetails_ShouldReturnBookDTODetails() {
        // Arrange
        BookDTODetails bookDTO = new BookDTODetails();
        Book book = new Book();
        when(bookMapper.convertToEntity(bookDTO)).thenReturn(book);
        when(bookMapper.convertToDtoDetails(book)).thenReturn(bookDTO);
        when(bookRepository.save(book)).thenReturn(book);

        // Act
        BookDTODetails createdBookDTO = bookService.createBook(bookDTO);

        // Assert
        assertNotNull(createdBookDTO);
        assertEquals(bookDTO, createdBookDTO);
    }

    @Test
    void deleteBookByID_BookFound_ShouldDeleteBook() {
        // Arrange
        UUID id = UUID.randomUUID();
        Book book = new Book();
        when(bookRepository.findById(id)).thenReturn(Optional.of(book));

        // Act
        assertDoesNotThrow(() -> bookService.deleteBookByID(id));

        // Assert
        verify(bookRepository, times(1)).deleteById(id);
    }

    @Test
    void deleteBookByID_BookNotFound_ShouldThrowApiException() {
        // Arrange
        UUID id = UUID.randomUUID();
        when(bookRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ApiException.class, () -> bookService.deleteBookByID(id));
    }

    @Test
    void updateBook_BookFoundAndValidDetails_ShouldUpdateBook() {
        // Arrange
        UUID id = UUID.randomUUID();
        BookDTODetails updatedBookDTO = new BookDTODetails();
        updatedBookDTO.setTitle("Updated Title");
        updatedBookDTO.setYear(2022);
        updatedBookDTO.setCopiesInLibrary(10);
        updatedBookDTO.setCopiesBorrowed(2);
        updatedBookDTO.setGenre(Genre.SCIENCE_FICTION);
        updatedBookDTO.setPublishingHouse("Updated Publishing House");

        Book book = new Book();
        when(bookRepository.findById(id)).thenReturn(Optional.of(book));

        // Act
        assertDoesNotThrow(() -> bookService.updateBook(id, updatedBookDTO));

        // Assert
        verify(bookRepository, times(1)).save(book);
        assertEquals("Updated Title", book.getTitle());
        assertEquals(2022, book.getYear());
        assertEquals(10, book.getCopiesInLibrary());
        assertEquals(2, book.getCopiesBorrowed());
        assertEquals(Genre.SCIENCE_FICTION, book.getGenre());
        assertEquals("Updated Publishing House", book.getPublishingHouse());
    }

    @Test
    void updateBook_BookNotFound_ShouldThrowApiException() {
        // Arrange
        UUID id = UUID.randomUUID();
        BookDTODetails updatedBookDTO = new BookDTODetails();

        when(bookRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ApiException.class, () -> bookService.updateBook(id, updatedBookDTO));
    }

}
