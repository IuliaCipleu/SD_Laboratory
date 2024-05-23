import { Author } from "./author";
import { Borrow } from "./borrow";
import { Genre } from "./genre";
export interface Book{
  id: string;
  isbn: string;
  title: string;
  authorDTO: Author;
  year: number;
  publishingHouse: string;
  copiesInLibrary: number;
  copiesBorrowed: number;
  borrows: Borrow[];
  genre: Genre;
}
