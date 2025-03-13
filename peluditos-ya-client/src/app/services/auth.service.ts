import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { catchError, Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = 'http://localhost:8080/api/auth'; 

  constructor(private http: HttpClient) {}

  register(userData: any): Observable<string> {
    return this.http.post(
      `${this.apiUrl}/signup`,
      userData,
      { responseType: 'text' }
    ).pipe(
      catchError(error => {
        throw new Error(error.error || 'Error desconocido');
      })
    );
  }
}
