package com.example.A1LibraryManagement.mapper;

import com.example.A1LibraryManagement.dto.BookDTO;
import com.example.A1LibraryManagement.dto.BorrowDTO;
import com.example.A1LibraryManagement.dto.BorrowerDTO;
import com.example.A1LibraryManagement.exception.ApiException;
import com.example.A1LibraryManagement.exception.ErrorEnum;
import com.example.A1LibraryManagement.model.Book;
import com.example.A1LibraryManagement.model.Borrow;
import com.example.A1LibraryManagement.model.Borrower;
import com.example.A1LibraryManagement.repository.BookRepository;
import com.example.A1LibraryManagement.repository.BorrowerRepository;
import com.example.A1LibraryManagement.service.BookService;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class BorrowMapper {
    private final BookRepository bookRepository;
    private final BorrowerRepository borrowerRepository;
    private final BookMapper bookMapper;
    private final BorrowerMapper borrowerMapper;
    private final BookService bookService;

    public BorrowMapper(BookRepository bookRepository, BorrowerRepository borrowerRepository, BookMapper bookMapper, BorrowerMapper borrowerMapper, BookService bookService) {
        this.bookRepository = bookRepository;
        this.borrowerRepository = borrowerRepository;
        this.bookMapper = bookMapper;
        this.borrowerMapper = borrowerMapper;
        this.bookService = bookService;
    }


    public BorrowDTO convertToDto(Borrow borrow){
        if (borrow == null) {
            //throw new ApiException(ErrorEnum.BOOK_NOT_FOUND);
            return null;
        }
        Book bookDTO;
        //bookDTO.setTitle(borrow.getBook().getTitle());

        bookDTO = borrow.getBook();
        if (bookDTO == null) {
            // Handle the case where the Book is null
            throw new ApiException(ErrorEnum.BOOK_NOT_FOUND);
        }
        Borrower borrower;
        //borrower.setName(borrow.getBorrower().getName());
        //borrower.setSurname(borrow.getBorrower().getSurname());
        borrower = borrow.getBorrower();
        if (borrower == null) {
            // Handle the case where the Borrower is null
            throw new ApiException(ErrorEnum.BORROWER_NOT_FOUND);
        }
        return BorrowDTO.builder()
                .bookISBN(bookDTO.getISBN())
                .borrowerID(borrower.getID())
                .borrowDate(borrow.getBorrowDate())
                .returnDate(borrow.getReturnDate())
                .build();
    }

    public Borrow convertToEntity(BorrowDTO borrowDTO){
        Borrow borrow = new Borrow();
       /* if (borrowDTO.getBookISBN()!=null){
            Book book = bookRepository.findByISBN(borrowDTO.getBookISBN())
                    .orElse(null);
            if(book == null){
                //book = bookMapper.convertToEntity(borrowDTO.getBookDTO());
                throw new ApiException(ErrorEnum.BOOK_NOT_FOUND);
            }
            borrow.setBook(book);
        }
        if (borrowDTO.getBorrowerID()!=null){
            Borrower borrower = borrowerRepository.findById(borrowDTO.getBorrowerID())
                    .orElse(null);
            if(borrower == null){
                //borrowerName = borrowerMapper.convertToEntity(borrowDTO.getBorrowerDTO());
                throw new ApiException(ErrorEnum.BORROWER_NOT_FOUND);
            }
            borrow.setBorrower(borrower);
        }
        *//*borrow.setBook(borrowDTO.getBookDTO());
        borrow.setBorrower(borrowDTO.getBorrowerDTO());*/
        borrow.setBorrowDate(borrowDTO.getBorrowDate());
        if(borrowDTO.getReturnDate()!= null){
        borrow.setReturnDate(borrowDTO.getReturnDate());}
        Optional<Book> bookOptional = bookRepository.findByISBN(borrowDTO.getBookISBN());
        if(bookOptional.isEmpty()){
            throw new ApiException(ErrorEnum.BOOK_NOT_FOUND);
        }
        Book book = bookOptional.get();
        borrow.setBook(book);
        Optional<Borrower> borrowerOptional = borrowerRepository.findById(borrowDTO.getBorrowerID());
        if(borrowerOptional.isEmpty()){
            throw new ApiException((ErrorEnum.BORROWER_NOT_FOUND));
        }
        Borrower borrower = borrowerOptional.get();
        borrow.setBorrower(borrower);
        return borrow;
    }
}
