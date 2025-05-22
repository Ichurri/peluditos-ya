import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AdminShelterService {
  private apiUrl = `${environment.apiBaseUrl}/shelter-requests`;

  constructor(private http: HttpClient) {}

  getApprovedShelters(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/approved`);
  }

  getPendingShelters(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/pending`);
  }

  approveShelter(id: number): Observable<any> {
    return this.http.put(`${this.apiUrl}/${id}/status?status=APPROVED`, {});
  }

  rejectShelter(id: number): Observable<any> {
    return this.http.put(`${this.apiUrl}/${id}/status?status=REJECTED`, {});
  }

  deleteShelter(id: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/${id}`);
  }
}
