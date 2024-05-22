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

@Component({
  selector: 'app-root',
  templateUrl: './borrower.component.html',
  styleUrls: ['./book.component.css']
})
export class BorrowerComponent implements OnInit {
  title = 'bookapp';
  public borrowers: Borrower[] = [];
  borrows: Borrow[] = [];
  searchCategory: string = 'id'; // Default search category
  searchQuery: string = '';
  createName: string = '';
  createSurname: string = '';
  createEmail: string = '';
  createDateOfBirth: Date = new Date();
  updateId: string = '';
  updateName: string = '';
  updateSurname: string = '';
  updateEmail: string = '';
  updateDateOfBirth: Date = new Date();
  deleteId: string = '';
  borrowerId: string = '';

  constructor(private borrowerService: BorrowerService, private userService: UserService) {}

  ngOnInit() {
    this.getBorrowers();
    this.borrowerId = uuidv4();
  }

  public getBorrowers(): void {
    if (this.userService.isAuthenticated()) {
      this.userService.getBorrowers().subscribe(
        (response: Borrower[]) => {
          this.borrowers = response;
          console.log(this.borrowers);
        },
        (error: HttpErrorResponse) => {
          alert(error.message);
        }
      );
    } else {
      console.log('Please log in to search for Borrowers.');
    }
  }


  public getBorrowerByID(): void {
    this.userService.getBorrowerByID(this.searchQuery).subscribe(
      (response: any) => {
        this.borrowers = [response];
        console.log('Borrower:', response);
      },
      (error: any) => {
        console.error('Error:', error);
        // Handle error
      }
    );
  }

  resetSearch() {
    this.searchQuery = ''; 
    this.getBorrowers(); 
  }

  createBorrower() {
    const newBorrower: Borrower = {
      id: this.borrowerId,
      name: this.createName,
      surname: this.createSurname,
      email: this.createEmail,
      dateOfBirth: this.createDateOfBirth,
      borrows: []
    };

    this.addBorrower(newBorrower);
  }

  addBorrower(borrower: Borrower) {
    this.userService.addBorrower(borrower).subscribe(
      (response: Borrower) => {
        console.log('Borrower added:', response);
        this.getBorrowers(); // Refresh book list
      },
      (error: any) => {
        console.error('Error adding Borrower:', error);
        // Handle error
      }
    );
  }

  editBorrower(borrower: Borrower) {
    this.updateId = borrower.id;
    this.updateName = borrower.name;
    this.updateSurname = borrower.surname;
    this.updateEmail = borrower.email;
    this.updateDateOfBirth = borrower.dateOfBirth;
  }

  updateBorrower() {
    this.userService.getBorrowerByID(this.updateId).subscribe(
      (borrower: Borrower) => {
        const updatedBorrower: Borrower = {
          id: this.updateId,
          name: this.updateName,
          surname: this.updateSurname,
          email: this.updateEmail,
          dateOfBirth: this.updateDateOfBirth,
          borrows: borrower.borrows 
        };
        
        this.userService.updateBorrower(updatedBorrower).subscribe(
          (response: Borrower) => {
            console.log('Borrower updated:', response);
            this.getBorrowers(); // Refresh book list
          },
          (error: any) => {
            console.error('Error updating Borrower:', error);
            // Handle error
          }
        );
      },
      (error: any) => {
        console.error('Error fetching Borrower:', error);
        // Handle error
      }
    );
  }

  deleteBorrower(borrowerId: string) {
    this.userService.deleteBorrower(borrowerId).subscribe(
      () => {
        console.log('Borrower deleted');
        this.getBorrowers(); // Refresh book list
      },
      (error: any) => {
        console.error('Error deleting Borrower:', error);
        //alert(error.text);
        // Handle error
      }
    );
  }
}
