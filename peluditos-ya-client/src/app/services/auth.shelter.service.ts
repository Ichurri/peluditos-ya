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

  getShelterById(id: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl2}/${id}`);
  }

  updateShelter(shelterId: number, shelterData: any): Observable<any> {
    const formData = new FormData();

    // Add basic shelter info
    if (shelterData.name) formData.append('name', shelterData.name);
    if (shelterData.email) formData.append('email', shelterData.email);
    if (shelterData.city) formData.append('city', shelterData.city);
    if (shelterData.phone) formData.append('phone', shelterData.phone);
    if (shelterData.shelterName) formData.append('shelterName', shelterData.shelterName);
    if (shelterData.shelterAddress) formData.append('shelterAddress', shelterData.shelterAddress);
    if (shelterData.description) formData.append('description', shelterData.description);

    // Add location
    if (shelterData.latitude) formData.append('latitude', shelterData.latitude.toString());
    if (shelterData.longitude) formData.append('longitude', shelterData.longitude.toString());

    // Add document file if provided
    if (shelterData.documentFile) formData.append('documentFile', shelterData.documentFile);

    return this.http.put(`${this.apiUrl2}/${shelterId}`, formData);
  }
}
