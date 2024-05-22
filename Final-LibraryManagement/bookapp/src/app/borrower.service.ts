import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Book } from './book';
import { environment } from '../environments/environment';
import { Borrower } from './borrower';

@Injectable({ providedIn: 'root' })
export class BorrowerService {
  private apiServerUrl = environment.apiBaseUrl;
  private token: string = '';

  constructor(private http: HttpClient) {}

  setToken(token: string): void {
    this.token = token;
  }

  public getBorrowers(): Observable<Borrower[]> {
    return this.http.get<Borrower[]>(`${this.apiServerUrl}/borrowers/`);
  }

  public getBorrowerByID(id: string, token: string): Observable<any> {
    const headers = new HttpHeaders().set('Authorization', 'Bearer ' + token);
    return this.http.get<any>(`${this.apiServerUrl}/borrowers/ID/${id}`, { headers });
  }

  public getBorrowerByEmail(email: string, token: string): Observable<any> {
    const headers = new HttpHeaders().set('Authorization', 'Bearer ' + token);
    return this.http.get<any>(`${this.apiServerUrl}/borrowers/email/${email}`, { headers });
  }

  public addBorrower(borrower: Borrower): Observable<Borrower> {
    return this.http.post<Borrower>(`${this.apiServerUrl}/borrowers/`, borrower);
  }

  public updateBorrower(borrower: Borrower): Observable<Borrower> {
    return this.http.put<Borrower>(`${this.apiServerUrl}/borrowers/update`, borrower);
  }

  public deleteBorrower(borrowerId: string): Observable<void> {
    return this.http.delete<void>(`${this.apiServerUrl}/borrowers/delete/${borrowerId}`);
  }
}
