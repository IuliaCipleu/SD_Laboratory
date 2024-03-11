package com.example.A1LibraryManagement.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.sql.Date;
import java.util.UUID;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Borrow {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private UUID ID;
    @ManyToOne
    @JoinColumn(name = "bookISBN")
    @JsonManagedReference("book-borrow")
    @NotNull
    private Book book;
    @ManyToOne
    @JoinColumn(name = "borrowerID")
    @JsonManagedReference("borrower-borrow")
    @NotNull
    private Borrower borrower;
    @NotNull
    private Date borrowDate;
    @Column(nullable = true)
    private Date returnDate;

    public UUID getID() {
        return ID;
    }

    public void setID(UUID ID) {
        this.ID = ID;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Borrower getBorrower() {
        return borrower;
    }

    public void setBorrower(Borrower borrower) {
        this.borrower = borrower;
    }

    public Date getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(Date borrowDate) {
        this.borrowDate = borrowDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }
}
