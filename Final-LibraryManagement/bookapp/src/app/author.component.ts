import { Component, OnInit } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';
import { Book } from './book';
import { UserService } from './user-login/user.service';
import { v4 as uuidv4 } from 'uuid';
import { Author } from './author';
import { AuthorService } from './author.service';

@Component({
  selector: 'app-root',
  templateUrl: './author.component.html',
  styleUrls: ['./book.component.css']
})
export class AuthorComponent implements OnInit {
  title = 'bookapp';
  public authors: Author[] = [];
  books: Book[] = [];
  searchCategory: string = 'id'; // Default search category
  searchQuery: string = '';
  createName: string = '';
  createSurname: string = '';
   updateId: string = '';
   updateName: string = '';
   updateSurname: string = '';
   deleteId: string = '';
   authorId: string = '';

  constructor(private authorService: AuthorService, private userService: UserService) {}

  ngOnInit() {
    this.getAuthors();
    this.authorId = uuidv4();
  }

  public getAuthors(): void {
    if (this.userService.isAuthenticated()) {
      this.userService.getAuthors().subscribe(
        (response: Author[]) => {
          this.authors = response;
          console.log(this.authors);
        },
        (error: HttpErrorResponse) => {
          alert(error.message);
        }
      );
    } else {
      console.log('Please log in to search for Authors.');
    }
  }

  searchAuthors() {
    if (!this.searchQuery.trim()) {
      console.log("Search query is empty.");
      return;
    }

    if (this.searchCategory === 'id') {
      this.getAuthorByID();
    } else if (this.searchCategory === 'name') {
      this.getAuthorByName();
    } else if (this.searchCategory === 'surname'){
        this.getAuthorBySurname();
    }
  }

  public getAuthorByID(): void {
    this.userService.getAuthorByID(this.searchQuery).subscribe(
      (response: any) => {
        this.authors = [response];
        console.log('Author:', response);
      },
      (error: any) => {
        console.error('Error:', error);
        // Handle error
      }
    );
  }

  public getAuthorByName(): void {
    this.userService.getAuthorByName(this.searchQuery).subscribe(
      (response: any) => {
        this.authors = [response];
        console.log('Author:', response);
      },
      (error: any) => {
        console.error('Error:', error);
        // Handle error
      }
    );
  }

  public getAuthorBySurname(): void {
    this.userService.getAuthorBySurname(this.searchQuery).subscribe(
      (response: any) => {
        this.authors = [response];
        console.log('Author:', response);
      },
      (error: any) => {
        console.error('Error:', error);
        // Handle error
      }
    );
  }

  resetSearch() {
    this.searchQuery = ''; 
    this.getAuthors(); 
  }

  createAuthor() {
    const newAuthor: Author = {
      id: this.authorId,
      name: this.createName,
      surname: this.createSurname,
      books: []
    };

    this.addAuthor(newAuthor);
  }

  addAuthor(author: Author) {
    this.userService.addAuthor(author).subscribe(
      (response: Author) => {
        console.log('Author added:', response);
        this.getAuthors(); // Refresh book list
      },
      (error: any) => {
        console.error('Error adding Author:', error);
        // Handle error
      }
    );
  }

  editAuthor(author: Author) {
    this.updateId = author.id;
    this.updateName = author.name;
    this.updateSurname = author.surname;
  }

  updateAuthor() {
    this.userService.getAuthorByID(this.updateId).subscribe(
      (author: Author) => {
        const updatedAuthor: Author = {
          id: this.updateId,
          name: this.updateName,
          surname: this.updateSurname,
          books: author.books
        };

        this.userService.updateAuthor(updatedAuthor).subscribe(
          (response: Author) => {
            console.log('Author updated:', response);
            this.getAuthors();
          },
          (error: any) => {
            console.error('Error updating Author:', error);
          }
        );
      },
      (error: any) => {
        console.error('Error fetching Author:', error);
      }
    );
  }

  deleteAuthor(authorId: string) {
    this.userService.deleteAuthor(authorId).subscribe(
      () => {
        console.log('Author deleted');
        this.getAuthors();
      },
      (error: any) => {
        console.error('Error deleting Author:', error);
      }
    );
  }
}
