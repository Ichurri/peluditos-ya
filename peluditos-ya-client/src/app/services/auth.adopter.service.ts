import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, BehaviorSubject } from 'rxjs';
import { map } from 'rxjs/operators';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AuthAdopterService {
  private isAdminSubject = new BehaviorSubject<boolean>(false);
  isAdmin$ = this.isAdminSubject.asObservable();

  private apiUrl = `${environment.apiBaseUrl}/auth`;

  constructor(private http: HttpClient) {}

  register(userData: any): Observable<string> {
    return this.http.post(
      `${this.apiUrl}/signup-adopter`, 
      userData,
      { responseType: 'text' }
    );
  }

  login(credentials: { email: string; password: string }): Observable<any> {
    return this.http.post<{ message: string; role: string; admin: boolean; userId: number }>(
      `${this.apiUrl}/login`,
      credentials
    ).pipe(
      map(response => {
        this.isAdminSubject.next(response.admin);
        console.log('isAdmin:', response.admin);
        localStorage.setItem('userId', response.userId.toString());
        return response;
      })
    );
  }

  isLoggedIn(): boolean {
    return localStorage.getItem('userId') !== null;
  }
  
  logout() {
    this.isAdminSubject.next(false);
    localStorage.clear();
  }
}
