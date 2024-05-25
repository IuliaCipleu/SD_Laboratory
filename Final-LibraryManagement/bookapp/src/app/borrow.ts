import { Book } from "./book";
import { Borrower } from "./borrower";

export interface Borrow {
    id: string;
    borrower: Borrower,
    borrowerDTO: Borrower,
    book: Book;
    bookDTO: Book;
    bookISBN: string,
    borrowerID: string,
    borrowDate: Date;
    returnDate: Date | null;
  }