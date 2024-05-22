import { Borrow } from "./borrow";

export interface Borrower {
    id: string,
    name: string, 
    surname: string,
    dateOfBirth: Date,
    email: string,
    borrows: Borrow[];
}