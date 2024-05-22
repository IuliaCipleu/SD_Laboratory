import { Component, OnInit } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { Book } from './book';
import { BookService } from './book.service';
import { HttpErrorResponse } from '@angular/common/http';
import { Author } from './author';
import { AuthorService } from './author.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit{
  title = 'bookapp';
  public books: Book[] = [];
  public authors: Author[] = [];

  constructor(private bookService: BookService, private authorService: AuthorService){}

  ngOnInit(){
    this.getBooks();
    //this.getAuthors();
  }

  public getBooks(): void {
    this.bookService.getBooks().subscribe(
      (response: Book[]) => {
        this.books = response;
        console.log(this.books);
      },
      (error: HttpErrorResponse)=>{
        alert(error.message);
      }
      )
  }

  // public getAuthors(): void {
  //   this.authorService.getAuthors().subscribe(
  //     (response: Author[]) => {
  //       this.authors = response;
  //       console.log(this.authors);
  //     },
  //     (error: HttpErrorResponse)=>{
  //       alert(error.message);
  //     }
  //     )
  // }
}
