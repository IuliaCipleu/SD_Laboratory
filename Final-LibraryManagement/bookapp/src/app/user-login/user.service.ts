import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Observable, catchError, throwError } from 'rxjs';
import { User } from './user';
import { environment } from '../../environments/environment';
import { LoginDTO } from './login.dto';
import { BookService } from '../book.service';
import { Book } from '../book';
import { Author } from '../author';
import { Borrower } from '../borrower';
import { Borrow } from '../borrow';
import { EMPTY } from 'rxjs';
import { AuthorService } from '../author.service';

@Injectable({ providedIn: 'root' })
export class UserService {
  private apiServerUrl = environment.apiBaseUrl;
  private tokenKey = 'auth_token'; // Key to store token in localStorage

  constructor(private http: HttpClient, private bookService: BookService, private authorService: AuthorService) {}

  public getUsers(): Observable<User[]> {
    return this.http.get<User[]>(`${this.apiServerUrl}/users/`);
  }

  public addUser(user: User): Observable<User> {
    return this.http.post<User>(`${this.apiServerUrl}/users/`, user);
  }

  public updateUser(user: User): Observable<User> {
    return this.http.put<User>(`${this.apiServerUrl}/users/update`, user);
  }

  public deleteUser(userId: string): Observable<void> {
    return this.http.delete<void>(`${this.apiServerUrl}/users/delete/${userId}`);
  }

  public login(loginDTO: LoginDTO): Observable<string> {
    return this.http.post(`${this.apiServerUrl}/auth/signin`, loginDTO, { responseType: 'text' });
  }

  public registerSimpleUser(user: User): Observable<User> {
    return this.http.post<User>(`${this.apiServerUrl}/auth/signup/simpleUser`, user);
  }

  public registerAdmin(user: User): Observable<User> {
    return this.http.post<User>(`${this.apiServerUrl}/auth/signup/admin`, user);
  }

  public logout(): void {
    localStorage.removeItem(this.tokenKey);
  }

  public setToken(token: string): void {
    localStorage.setItem(this.tokenKey, token); // Store token in localStorage
    this.bookService.setToken(token);
    this.authorService.setToken(token);
  }

  public getToken(): string | null {
    const token = localStorage.getItem(this.tokenKey);
    console.log('Retrieved token:', token);
    return token;
  }

  public isAuthenticated(): boolean {
    return !!this.getToken(); // Check if token exists
  }

  public getBooks(): Observable<Book[]> {
    if (!this.isAuthenticated()) {
      console.log('Please log in to search for books.');
      alert('Please log in to search for books.');
      return EMPTY; // Return an empty observable
    }
    return this.http.get<Book[]>(`${this.apiServerUrl}/books/`);
  }

  public getBookByISBN(isbn: string): Observable<any> {
    const token = this.getToken();
    if (!token) {
      console.log('Please log in to search for books.');
      throw new Error('Please log in to search for books.'); 
    }
    const headers = new HttpHeaders().set('Authorization', 'Bearer ' + token);
    return this.http.get<any>(`${this.apiServerUrl}/books/ISBN/${isbn}`, { headers });
  }

  public getBookByTitle(title: string): Observable<any> {
    const token = this.getToken();
    if (!token) {
      console.log('Please log in to search for books.');
      throw new Error('Please log in to search for books.'); 
    }
    const headers = new HttpHeaders().set('Authorization', 'Bearer ' + token);
    return this.http.get<any>(`${this.apiServerUrl}/books/title/${title}`, { headers });
  }

  public addBook(book: Book): Observable<Book> {
    const token = this.getToken();
    if (!token) {
      console.log('Please log in to add books.');
      throw new Error('Please log in to add books.');
    }
    const headers = new HttpHeaders().set('Authorization', 'Bearer ' + token);
    return this.http.post<Book>(`${this.apiServerUrl}/books/`, book, { headers });
  }

  public updateBook(book: Book): Observable<Book> {
    const token = this.getToken();
    if (!token) {
      console.log('Please log in to update books.');
      throw new Error('Please log in to update books.'); 
    }
    const headers = new HttpHeaders().set('Authorization', 'Bearer ' + token);
    return this.http.put<Book>(`${this.apiServerUrl}/books/${book.id}`, book, { headers });
  }

  public deleteBook(bookId: string): Observable<void> {
    const token = this.getToken();
    if (!token) {
      console.log('Please log in to delete books.');
      throw new Error('Please log in to delete books.'); 
    }
    const headers = new HttpHeaders().set('Authorization', 'Bearer ' + token);
    return this.http.delete<void>(`${this.apiServerUrl}/books/${bookId}`, { headers });
  }

  public getAuthors(): Observable<Author[]> {
    if (!this.isAuthenticated()) {
      console.log('Please log in to search for authors.');
      return EMPTY; // Return an empty observable
    }
    
    // Get token and set headers
    const token = this.getToken();
    const headers = new HttpHeaders().set('Authorization', 'Bearer ' + token);

    // Send request with headers
    return this.http.get<Author[]>(`${this.apiServerUrl}/authors/allDetails/`, { headers });
  }

  public getAuthorByID(id: string): Observable<any> {
    const token = this.getToken();
    if (!token) {
      console.log('Please log in to search for authors.');
      throw new Error('Please log in to search for authors.'); 
    }
    const headers = new HttpHeaders().set('Authorization', 'Bearer ' + token);
    return this.http.get<any>(`${this.apiServerUrl}/authors/ID/${id}`, { headers });
  }

  public getAuthorByName(name: string): Observable<any> {
    const token = this.getToken();
    if (!token) {
      console.log('Please log in to search for Authors.');
      throw new Error('Please log in to search for Authors.'); 
    }
    const headers = new HttpHeaders().set('Authorization', 'Bearer ' + token);
    return this.http.get<any>(`${this.apiServerUrl}/authors/name/${name}`, { headers });
  }

  public getAuthorBySurname(surname: string): Observable<any> {
    const token = this.getToken();
    if (!token) {
      console.log('Please log in to search for Authors.');
      throw new Error('Please log in to search for Authors.'); 
    }
    const headers = new HttpHeaders().set('Authorization', 'Bearer ' + token);
    return this.http.get<any>(`${this.apiServerUrl}/authors/surname/${surname}`, { headers });
  }

  public addAuthor(author: Author): Observable<Author> {
    const token = this.getToken();
    if (!token) {
      console.log('Please log in to add Authors.');
      throw new Error('Please log in to add Authors.'); 
    }
    const headers = new HttpHeaders().set('Authorization', 'Bearer ' + token);
    return this.http.post<Author>(`${this.apiServerUrl}/authors/`, author, { headers });
  }

  public updateAuthor(author: Author): Observable<Author> {
    const token = this.getToken();
    if (!token) {
      console.log('Please log in to update Authors.');
      throw new Error('Please log in to update Authors.'); 
    }
    const headers = new HttpHeaders().set('Authorization', 'Bearer ' + token);
    return this.http.put<Author>(`${this.apiServerUrl}/authors/${author.id}`, author, { headers });
  }

  deleteAuthor(authorId: string): Observable<void> {
    const token = this.getToken();
    if (!token) {
      console.log('Please log in to delete Authors.');
      throw new Error('Please log in to delete Authors.');
    }
    
    const headers = new HttpHeaders().set('Authorization', 'Bearer ' + token);
    const url = `${this.apiServerUrl}/authors/${authorId}`;
  
    return this.http.delete<void>(url, { headers }).pipe(
      catchError((error: HttpErrorResponse) => {
        console.error('Error deleting Author:', error);
        console.log('Error object:', error); // Log the entire error object
        console.warn('Error message:', error.error); // Log the error message
        return throwError('Error deleting author!');
      })
    );
  }

  private createAuthHeaders(): HttpHeaders {
    const token = this.getToken();
    if (!token) {
      throw new Error('Please log in to perform this action.');
    }
    return new HttpHeaders().set('Authorization', 'Bearer ' + token);
  }
  
  public getBorrows(): Observable<Borrow[]> {
    const headers = this.createAuthHeaders();
    return this.http.get<Borrow[]>(`${this.apiServerUrl}/borrows/`, { headers });
  }

  public getBorrowByID(id: string): Observable<Borrow> {
    const headers = this.createAuthHeaders();
    return this.http.get<Borrow>(`${this.apiServerUrl}/borrows/ID/${id}`, { headers });
  }

  public getBorrowsReturned(): Observable<Borrow[]> {
    const headers = this.createAuthHeaders();
    return this.http.get<Borrow[]>(`${this.apiServerUrl}/borrows/nonreturned/`, { headers });
  }

  public getBorrowsNonReturned(): Observable<Borrow[]> {
    const headers = this.createAuthHeaders();
    return this.http.get<Borrow[]>(`${this.apiServerUrl}/borrows/returned/`, { headers });
  }

  public addBorrow(borrow: Borrow): Observable<Borrow> {
    const headers = this.createAuthHeaders();
    return this.http.post<Borrow>(`${this.apiServerUrl}/borrows/`, borrow, { headers });
  }

  public deleteBorrow(borrowId: string): Observable<void> {
    const headers = this.createAuthHeaders();
    return this.http.delete<void>(`${this.apiServerUrl}/borrows/${borrowId}`, { headers });
  }

  public updateBorrow(borrow: Borrow): Observable<Borrow> {
    const headers = this.createAuthHeaders();
    return this.http.put<Borrow>(`${this.apiServerUrl}/borrows/${borrow.id}`, borrow, { headers });
  }

  public getBorrowers(): Observable<Borrower[]> {
    if (!this.isAuthenticated()) {
      console.log('Please log in to search for borrowers.');
      return EMPTY; // Return an empty observable
    }
    
    const token = this.getToken();
    const headers = new HttpHeaders().set('Authorization', 'Bearer ' + token);
    return this.http.get<Borrower[]>(`${this.apiServerUrl}/borrowers/`, { headers });
  }

  public getBorrowerByID(id: string): Observable<any> {
    const token = this.getToken();
    if (!token) {
      console.log('Please log in to search for borrowers.');
      throw new Error('Please log in to search for borrowers.'); 
    }
    const headers = new HttpHeaders().set('Authorization', 'Bearer ' + token);
    return this.http.get<any>(`${this.apiServerUrl}/borrowers/ID/${id}`, { headers });
  }

  public getBorrowerByEmail(email: string): Observable<any> {
    const token = this.getToken();
    if (!token) {
      console.log('Please log in to search for borrowers.');
      throw new Error('Please log in to search for borrowers.'); 
    }
    const headers = new HttpHeaders().set('Authorization', 'Bearer ' + token);
    return this.http.get<any>(`${this.apiServerUrl}/borrowers/email/${email}`, { headers });
  }

  public addBorrower(borrower: Borrower): Observable<Borrower> {
    const token = this.getToken();
    if (!token) {
      console.log('Please log in to create borrower.');
      throw new Error('Please log in to create borrower.'); 
    }
    const headers = new HttpHeaders().set('Authorization', 'Bearer ' + token);
    return this.http.post<Borrower>(`${this.apiServerUrl}/borrowers/`, borrower, { headers });
  }

  public deleteBorrower(borrowerId: string): Observable<void> {
    const token = this.getToken();
    if (!token) {
      console.log('Please log in to delete borrower.');
      throw new Error('Please log in to delete borrower.'); 
    }
    const headers = new HttpHeaders().set('Authorization', 'Bearer ' + token);
    return this.http.delete<void>(`${this.apiServerUrl}/borrowers/${borrowerId}`, { headers });
  }

  public updateBorrower(borrower: Borrower): Observable<Borrower> {
    const token = this.getToken();
    if (!token) {
      console.log('Please log in to update borrower.');
      throw new Error('Please log in to update borrower.'); 
    }
    const headers = new HttpHeaders().set('Authorization', 'Bearer ' + token);
    return this.http.put<Borrower>(`${this.apiServerUrl}/borrowers/${borrower.id}`, borrower, { headers });
  }
  
}
