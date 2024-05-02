import { Component, OnInit } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';
import { Book } from './book';
import { UserService } from './user-login/user.service';
import { v4 as uuidv4 } from 'uuid';
import { Author } from './author';
import { AuthorService } from './author.service';
import { BorrowerService } from './borrower.service';
import { Borrower } from './borrower';
import { Borrow } from './borrow';
import { BorrowService } from './borrow.service';
import { Genre } from './genre';

@Component({
  selector: 'app-root',
  templateUrl: './borrow.component.html',
  styleUrls: ['./book.component.css']
})
export class BorrowComponent implements OnInit {
  title = 'bookapp';
  public borrows: Borrow[] = [];
  books: Book[] = [];
  searchCategory: string = 'id'; // Default search category
  searchQuery: string = '';
  createBookISBN: string = '';
  createBorrowerId: string = '';
  createBorrowDate: Date = new Date();
  createReturnedDate: Date | null = null;
  updateBorrowId: string = '';
  updateBookISBN: string = '';
  updateBorrowerId: string = '';
  updateBorrowDate: Date = new Date();
  updateReturnedDate: Date = new Date();
  deleteId: string = '';
  borrowId: string = '';

  constructor(private borrowService: BorrowService, private userService: UserService) {}

  ngOnInit() {
    this.getBorrows();
    this.borrowId = uuidv4();
  }

  public getBorrows(): void {
    if (this.userService.isAuthenticated()) {
      this.userService.getBorrows().subscribe(
        (response: Borrow[]) => {
          this.borrows = response;
          console.log(this.borrows);
        },
        (error: HttpErrorResponse) => {
          alert(error.message);
        }
      );
    } else {
      console.log('Please log in to search for Borrows.');
    }
  }


  public getBorrowByID(): void {
    this.userService.getBorrowByID(this.searchQuery).subscribe(
      (response: any) => {
        this.borrows = [response];
        console.log('Borrow:', response);
      },
      (error: any) => {
        console.error('Error:', error);
        // Handle error
      }
    );
  }

  resetSearch() {
    this.searchQuery = ''; 
    this.getBorrows(); 
  }

  createBorrow() {
    this.userService.getBorrowerByID(this.createBorrowerId).subscribe(
      (borrower: Borrower) => {
        this.userService.getBookByISBN(this.createBookISBN).subscribe(
          (book: Book) => {
            const newBorrow: Borrow = {
              id: this.borrowId,
              bookISBN: this.createBookISBN, // Assign the fetched book
              borrowerID: this.createBorrowerId,
              borrowDate: this.createBorrowDate,
              returnDate: this.createReturnedDate
            };
  
            this.addBorrow(newBorrow);
          },
          (error: any) => {
            console.error('Error fetching book:', error);
            // Handle error
          }
        );
      },
      (error: any) => {
        console.error('Error fetching borrower:', error);
        // Handle error
      }
    );
  }

  addBorrow(borrow: Borrow) {
    this.userService.addBorrow(borrow).subscribe(
      (response: Borrow) => {
        console.log('Borrow added:', response);
        this.getBorrows(); // Refresh book list
      },
      (error: any) => {
        console.error('Error adding Borrow:', error);
        // Handle error
      }
    );
  }

  updateBorrow() {
    this.userService.getBorrowerByID(this.updateBorrowerId).subscribe(
      (borrower: Borrower) => {
        this.userService.getBorrowByID(this.updateBorrowId).subscribe(
          (borrow: Borrow) => {
            this.userService.getBookByISBN(this.updateBookISBN).subscribe(
              (book: Book) => {
                const updatedBorrow: Borrow = {
                  id: this.updateBorrowId,
                  bookISBN: this.updateBookISBN, // Assign the fetched book
                  borrowerID: this.updateBorrowerId,
                  borrowDate: this.updateBorrowDate,
                  returnDate: this.updateReturnedDate
                };
  
                this.userService.updateBorrow(updatedBorrow).subscribe(
                  (response: Borrow) => {
                    console.log('Borrow updated:', response);
                    this.getBorrows(); // Refresh book list
                  },
                  (error: any) => {
                    console.error('Error updating Borrow:', error);
                    // Handle error
                  }
                );
              },
              (error: any) => {
                console.error('Error fetching book:', error);
                // Handle error
              }
            );
          },
          (error: any) => {
            console.error('Error fetching Borrow:', error);
            // Handle error
          }
        );
      },
      (error: any) => {
        console.error('Error fetching borrower:', error);
        // Handle error
      }
    );
  }

  deleteBorrow() {
    this.userService.deleteBorrow(this.deleteId).subscribe(
      () => {
        console.log('Borrow deleted');
        this.getBorrows(); // Refresh book list
      },
      (error: any) => {
        console.error('Error deleting Borrow:', error);
        //alert(error.text);
        // Handle error
      }
    );
  }
}
