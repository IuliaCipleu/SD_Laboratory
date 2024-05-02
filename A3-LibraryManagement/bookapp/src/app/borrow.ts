import { Book } from "./book";
import { Borrower } from "./borrower";

export interface Borrow {
    id: string;
    borrowerID: string,
    bookISBN: string;
    borrowDate: Date;
    returnDate: Date | null;
  }