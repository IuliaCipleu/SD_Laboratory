import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Book } from './book';
import { environment } from '../environments/environment';
import { Borrow } from './borrow';

@Injectable({ providedIn: 'root' })
export class BorrowService {
  private apiServerUrl = environment.apiBaseUrl;
  private token: string = '';

  constructor(private http: HttpClient) {}

  setToken(token: string): void {
    this.token = token;
  }

  public getBorrows(): Observable<Borrow[]> {
    return this.http.get<Borrow[]>(`${this.apiServerUrl}/borrowe/`);
  }

  public getBorrowByID(id: string, token: string): Observable<any> {
    const headers = new HttpHeaders().set('Authorization', 'Bearer ' + token);
    return this.http.get<any>(`${this.apiServerUrl}/borrows/ID/${id}`, { headers });
  }

  public addBorrow(borrow: Borrow): Observable<Borrow> {
    return this.http.post<Borrow>(`${this.apiServerUrl}/borrows/`, borrow);
  }

  public updateBorrow(borrow: Borrow): Observable<Borrow> {
    return this.http.put<Borrow>(`${this.apiServerUrl}/borrows/update`, borrow);
  }

  public deleteBorrow(borrowId: string): Observable<void> {
    return this.http.delete<void>(`${this.apiServerUrl}/borrows/delete/${borrowId}`);
  }
}
