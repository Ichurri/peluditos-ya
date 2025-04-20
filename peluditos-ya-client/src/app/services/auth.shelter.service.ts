import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthShelterService {
  private apiUrl = 'http://localhost:8080/api/auth';
  private apiUrl2 = 'http://localhost:8080/api/shelters';

  constructor(private http: HttpClient) {}

  submitShelterRequest(requestData: any): Observable<string> {
    return this.http.post(
      'http://localhost:8080/api/shelter-requests',
      requestData,
      { responseType: 'text' }
    );
  }
  

  register(userData: any): Observable<string> {
    return this.http.post(
      `${this.apiUrl}/signup-shelter`, 
      userData,
      { responseType: 'text' }
    );
  }

  login(credentials: { email: string; password: string }): Observable<any> {
    console.log('Enviando login:', credentials); // Verifica qué se envía
    return this.http.post<{ message: string; admin: boolean }>(
      `${this.apiUrl}/login-shelter`,
      credentials
    );
  }

  getShelters(): Observable<any[]> {
    return this.http.get<any[]>(this.apiUrl2);
  }

  deleteShelter(id: number): Observable<any> {
    return this.http.delete(`${this.apiUrl2}/${id}`);
  }
}
