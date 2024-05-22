import { Component, OnInit } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';
import { Book } from './book';
import { BookService } from './book.service';
import { UserService } from './user-login/user.service';
import { v4 as uuidv4 } from 'uuid';
import { Author } from './author';
import { Genre } from './genre';
import { AuthorService } from './author.service';

@Component({
  selector: 'app-root',
  templateUrl: './book.component.html',
  styleUrls: ['./book.component.css']
})
export class BookComponent implements OnInit {
  title = 'bookapp';
  public books: Book[] = [];
  book: any = {};
  searchCategory: string = 'isbn'; // Default search category
  searchQuery: string = '';
  createTitle: string = '';
  createAuthorName: string = '';
  createAuthorSurname: string = '';
  createYear: string = '';
  createPublishingHouse: string = '';
  createIsbn: string = '';
  createCopiesInLibrary: string = '';
  createCopiesBorrowed: string = '';
  createGenre: Genre = Genre.THRILLER;
  updateId: string = '';
  updateTitle: string = '';
  updateAuthor: Author = { id: '', name: '', surname: '', books: [] };
  updateYear: string = '';
  updatePublishingHouse: string = '';
  updateIsbn: string = '';
  updateCopiesInLibrary: string = '';
  updateCopiesBorrowed: string = '';
  updateGenre: Genre = Genre.THRILLER;
  deleteId: string = '';
  bookId: string = '';
  genres: string[] = [];

  constructor(private bookService: BookService, private userService: UserService, private authorService: AuthorService) {}

  ngOnInit() {
    this.getBooks();
    this.bookId = uuidv4();
    this.genres = Object.values(Genre).filter(value => typeof value === 'string');
  }

  public getBooks(): void {
    if (this.userService.isAuthenticated()) {
      this.userService.getBooks().subscribe(
        (response: Book[]) => {
          this.books = response;
          console.log(this.books);
        },
        (error: HttpErrorResponse) => {
          alert(error.message);
        }
      );
    } else {
      console.log('Please log in to search for books.');
    }
  }

  searchBooks() {
    if (!this.searchQuery.trim()) {
      console.log("Search query is empty.");
      return;
    }

    if (this.searchCategory === 'isbn') {
      this.getBookByISBN();
    } else if (this.searchCategory === 'title') {
      this.getBookByTitle();
    }
  }

  public getBookByISBN(): void {
    this.userService.getBookByISBN(this.searchQuery).subscribe(
      (response: any) => {
        this.books = [response];
        console.log('Book:', response);
      },
      (error: any) => {
        console.error('Error:', error);
        alert('Book not found!');
      }
    );
  }

  public getBookByTitle(): void {
    this.userService.getBookByTitle(this.searchQuery).subscribe(
      (response: any) => {
        this.books = [response];
        console.log('Book:', response);
      },
      (error: any) => {
        console.error('Error:', error);
        alert('Book not found!');
        // Handle error
      }
    );
  }

  resetSearch() {
    this.searchQuery = ''; // Reset the search query
    this.getBooks(); // Reload all books
  }

  createBook() {
    // Search for an author by both name and surname
    this.userService.getAuthorByName(this.createAuthorName).subscribe(
      (existingAuthorByName: Author) => {
        // If author found by name, search by surname
        if (existingAuthorByName) {
          this.userService.getAuthorBySurname(this.createAuthorSurname).subscribe(
            (existingAuthorBySurname: Author) => {
              // If author found by surname, create book with existing author
              if (existingAuthorBySurname) {
                this.createBookWithAuthor(existingAuthorBySurname);
              } else {
                // If author not found by surname, create new author
                this.createNewAuthor();
              }
            },
            (error: any) => {
              console.error('Error searching Author by surname:', error);
              alert('Error searching Author by surname!');
            }
          );
        } else {
          // If author not found by name, create new author
          this.createNewAuthor();
        }
      },
      (error: any) => {
        console.error('Error searching Author by name:', error);
        alert('Error searching Author by name!');
      }
    );
  }

  createNewAuthor() {
    const newAuthor: Author = {
      id: uuidv4(),
      name: this.createAuthorName,
      surname: this.createAuthorSurname,
      books: []
    };
    // Save the new author
    this.userService.addAuthor(newAuthor).subscribe(
      (createdAuthor: Author) => {
        // Once the new author is created, create the book with the new author
        this.createBookWithAuthor(createdAuthor);
      },
      (error: any) => {
        console.error('Error creating Author:', error);
        alert('Error creating Author!');
      }
    );
  }

  createBookWithAuthor(author: Author) {
    const newBook: Book = {
      id: this.bookId,
      isbn: this.createIsbn,
      title: this.createTitle,
      year: parseInt(this.createYear),
      author: author,
      publishingHouse: this.createPublishingHouse,
      copiesInLibrary: parseInt(this.createCopiesInLibrary),
      copiesBorrowed: parseInt(this.createCopiesBorrowed),
      genre: this.createGenre,
      borrows: []
    };

    // Add the book
    this.userService.addBook(newBook).subscribe(
      (response: Book) => {
        console.log('Book added:', response);
        // Refresh book list
        // Implement a function to refresh book list if needed
      },
      (error: any) => {
        console.error('Error adding Book:', error);
        alert('Error adding Book!');
      }
    );
  }

  editBook(book: Book) {
    this.updateId = book.id;
    this.updateTitle = book.title;
    this.updateAuthor = { ...book.author }; // Copy author details
    this.updateYear = book.year.toString();
    this.updatePublishingHouse = book.publishingHouse;
    this.updateIsbn = book.isbn;
    this.updateCopiesInLibrary = book.copiesInLibrary.toString();
    this.updateCopiesBorrowed = book.copiesBorrowed.toString();
    this.updateGenre = book.genre;
  }

  updateBook() {
    // Search for an author by both name and surname
    this.userService.getAuthorByName(this.updateAuthor.name).subscribe(
      (existingAuthorByName: Author) => {
        // If author found by name, search by surname
        if (existingAuthorByName) {
          this.userService.getAuthorBySurname(this.updateAuthor.surname).subscribe(
            (existingAuthorBySurname: Author) => {
              // If author found by surname, update book with existing author
              if (existingAuthorBySurname) {
                this.updateBookWithAuthor(existingAuthorBySurname);
              } else {
                // If author not found by surname, update existing author or create new one
                this.updateOrCreateAuthor(existingAuthorByName);
              }
            },
            (error: any) => {
              console.error('Error searching Author by surname:', error);
              alert('Error searching Author by surname!');
            }
          );
        } else {
          // If author not found by name, create new author
          this.updateOrCreateAuthor();
        }
      },
      (error: any) => {
        console.error('Error searching Author by name:', error);
        alert('Error searching Author by name!');
      }
    );
  }

  updateOrCreateAuthor(existingAuthor?: Author) {
    if (existingAuthor) {
      // If author exists, update the existing author
      this.updateAuthorMethod(existingAuthor);
    } else {
      // If author does not exist, create new author
      const newAuthor: Author = {
        id: uuidv4(),
        name: this.updateAuthor.name,
        surname: this.updateAuthor.surname,
        books: []
      };
      // Save the new author
      this.userService.addAuthor(newAuthor).subscribe(
        (createdAuthor: Author) => {
          // Once the new author is created, update the book with the new author
          this.updateBookWithAuthor(createdAuthor);
        },
        (error: any) => {
          console.error('Error creating Author:', error);
          // Handle error
        }
      );
    }
  }

  updateBookWithAuthor(author: Author) {
    const updatedBook: Book = {
      id: this.updateId,
      title: this.updateTitle,
      author: author,
      year: parseInt(this.updateYear),
      publishingHouse: this.updatePublishingHouse,
      isbn: this.updateIsbn,
      copiesInLibrary: parseInt(this.updateCopiesInLibrary),
      copiesBorrowed: parseInt(this.updateCopiesBorrowed),
      genre: this.updateGenre,
      borrows: [] // Assuming `borrows` is a property of the Book interface
    };

    // Update the book
    this.userService.updateBook(updatedBook).subscribe(
      (response: Book) => {
        console.log('Book updated:', response);
        // Refresh book list
        // Implement a function to refresh book list if needed
      },
      (error: any) => {
        console.error('Error updating book:', error);
        alert('Error updating book!');
      }
    );
  }

  updateAuthorMethod(author: Author) {
    const updatedAuthor: Author = {
      id: author.id,
      name: this.updateAuthor.name,
      surname: this.updateAuthor.surname,
      books: author.books
    };

    // Update the author
    this.userService.updateAuthor(updatedAuthor).subscribe(
      (response: Author) => {
        console.log('Author updated:', response);
        // Once the author is updated, update the book with the updated author
        this.updateBookWithAuthor(response);
      },
      (error: any) => {
        console.error('Error updating author:', error);
        // Handle error
      }
    );
  }

  deleteBook(bookId: string) {
    this.userService.deleteBook(bookId).subscribe(
      () => {
        console.log('Book deleted');
        this.getBooks();
      },
      (error: any) => {
        console.error('Error deleting Book:', error);
      }
    );
  }
}
