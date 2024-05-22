import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Book } from './book';
import { Author } from './author';
import { environment } from '../environments/environment';

@Injectable({ providedIn: 'root' })
export class AuthorService {
  private apiServerUrl = environment.apiBaseUrl;
  private token: string = '';

  constructor(private http: HttpClient) {}

  setToken(token: string): void {
    this.token = token;
  }

  public getAuthors(): Observable<Author[]> {
    return this.http.get<Author[]>(`${this.apiServerUrl}/authors/`);
  }

  public getAuthorById(id: string, token: string): Observable<any> {
    const headers = new HttpHeaders().set('Authorization', 'Bearer ' + token);
    return this.http.get<any>(`${this.apiServerUrl}/author/ID/${id}`, { headers });
  }
  

  public addAuthor(author: Author): Observable<Author> {
    return this.http.post<Author>(`${this.apiServerUrl}/authors/`, author);
  }

  public updateAuthor(author: Author): Observable<Author> {
    return this.http.put<Author>(`${this.apiServerUrl}/authors/update`, author);
  }

  public deleteAuthor(authorId: string): Observable<void> {
    return this.http.delete<void>(`${this.apiServerUrl}/authors/delete/${authorId}`);
  }
}
