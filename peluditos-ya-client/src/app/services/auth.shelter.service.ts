import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AuthShelterService {
  private apiUrl = `${environment.apiBaseUrl}/auth`;
  private apiUrl2 = `${environment.apiBaseUrl}/shelter-requests`;
  private shelterRequestUrl = `${environment.apiBaseUrl}/shelter-requests`;

  constructor(private http: HttpClient) {}

  submitShelterRequest(requestData: any): Observable<string> {
    return this.http.post(
      this.shelterRequestUrl,
      requestData,
      { responseType: 'text' }
    );
  }

  getApprovedShelters(): Observable<any[]> {
    return this.http.get<any[]>(`${this.shelterRequestUrl}/approved`);
  }

  register(userData: any): Observable<string> {
    return this.http.post(
      `${this.apiUrl}/signup-shelter`, 
      userData,
      { responseType: 'text' }
    );
  }

  login(credentials: { email: string; password: string }): Observable<any> {
    console.log('Enviando login:', credentials);
    return this.http.post<{ message: string; admin: boolean }>(
      `${this.apiUrl}/login`,
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
