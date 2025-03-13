import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = 'http://localhost:8080/api/auth/signup'; 

  constructor(private http: HttpClient) {}

  register(userData: any): Observable<string> {
    return this.http.post<string>(`${this.apiUrl}/signup`, userData);
  }
}
