import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AnimalService {

  private baseUrl = `${environment.apiBaseUrl}/animals`;

  constructor(private http: HttpClient) {}

  registrarAnimal(formulario: any, shelterRequestId: number): Observable<any> {
    const formData = new FormData();

    formData.append('name', formulario.name);
    formData.append('animal', formulario.animal); 
    formData.append('breed', formulario.breed);
    formData.append('age', formulario.age);
    formData.append('behavior', formulario.behavior); 
    formData.append('shelterId', shelterRequestId.toString());

    if (formulario.medicalFile) {
      formData.append('medicalFile', formulario.medicalFile);
    }

    if (formulario.photo) {
      formData.append('photo', formulario.photo);
    }

    return this.http.post(`${this.baseUrl}/register`, formData);
  }

  obtenerMascotas(): Observable<any> {
    return this.http.get(`${this.baseUrl}`);
  }
  
  getAnimalProfile(id: number): Observable<any> {
    return this.http.get(`${this.baseUrl}/${id}/profile`);
}
}
