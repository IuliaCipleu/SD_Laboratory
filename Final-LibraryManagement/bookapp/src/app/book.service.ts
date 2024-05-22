import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Book } from './book';
import { environment } from '../environments/environment';

@Injectable({ providedIn: 'root' })
export class BookService {
  private apiServerUrl = environment.apiBaseUrl;
  private token: string = '';

  constructor(private http: HttpClient) {}

  setToken(token: string): void {
    this.token = token;
  }

  public getBooks(): Observable<Book[]> {
    return this.http.get<Book[]>(`${this.apiServerUrl}/books/`);
  }

  public getBookByISBN(isbn: string, token: string): Observable<any> {
    const headers = new HttpHeaders().set('Authorization', 'Bearer ' + token);
    return this.http.get<any>(`${this.apiServerUrl}/books/ISBN/${isbn}`, { headers });
  }

  public addBook(book: Book): Observable<Book> {
    return this.http.post<Book>(`${this.apiServerUrl}/books/`, book);
  }

  public updateBook(book: Book): Observable<Book> {
    return this.http.put<Book>(`${this.apiServerUrl}/books/update`, book);
  }

  public deleteBook(bookId: string): Observable<void> {
    return this.http.delete<void>(`${this.apiServerUrl}/books/delete/${bookId}`);
  }
}
