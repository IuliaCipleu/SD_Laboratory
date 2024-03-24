package com.example.A1LibraryManagement.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID ID;
    @NotNull
    private String ISBN;
    @NotNull
    private String title;
    @ManyToOne
    @JoinColumn(name = "authorID")
    @JsonManagedReference
    @NotNull
    private Author author;
    @NotNull
    private int year;
    @NotNull
    private String publishingHouse;
    private int copiesInLibrary = 0;
    private int copiesBorrowed = 0;
    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference("book-borrow")
    private List<Borrow> borrows;

    private Genre genre;

    public UUID getID() {
        return ID;
    }

    public void setID(UUID ID) {
        this.ID = ID;
    }

    public String getISBN() {
        return ISBN;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Author getAuthorID() {
        return author;
    }

    public void setAuthorID(Author authorID) {
        this.author = authorID;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getPublishingHouse() {
        return publishingHouse;
    }

    public void setPublishingHouse(String publishingHouse) {
        this.publishingHouse = publishingHouse;
    }

    public int getCopiesInLibrary() {
        return copiesInLibrary;
    }

    public void setCopiesInLibrary(int copiesInLibrary) {
        this.copiesInLibrary = copiesInLibrary;
    }

    public int getCopiesBorrowed() {
        return copiesBorrowed;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public List<Borrow> getBorrows() {
        return borrows;
    }

    public void setBorrows(List<Borrow> borrows) {
        this.borrows = borrows;
    }

    public void setCopiesBorrowed(int copiesBorrowed) {
        this.copiesBorrowed = copiesBorrowed;
    }
}
