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

    if (formulario.photoUrl) {
    formData.append('photoUrl', formulario.photoUrl);
    }

    for (const pair of formData.entries()) {
      console.log(`${pair[0]}: ${pair[1]}`);
    }

    return this.http.post(`${this.baseUrl}/register`, formData);
  }

  obtenerMascotas(): Observable<any> {
    return this.http.get(`${this.baseUrl}`);
  }
  
  getAnimalProfile(id: number): Observable<any> {
    return this.http.get(`${this.baseUrl}/${id}/profile`);
  }

  asignarPadrino(animalId: number, userId: number) {
    return this.http.put<any>(`${this.baseUrl}/${animalId}/asignar-padrino?userId=${userId}`, {});
  }

  updateAnimal(animalId: number, animalData: any): Observable<any> {
    const formData = new FormData();

    // Add basic fields
    if (animalData.name) formData.append('name', animalData.name);
    if (animalData.animal) formData.append('animal', animalData.animal);
    if (animalData.breed) formData.append('breed', animalData.breed);
    if (animalData.age) formData.append('age', animalData.age.toString());
    if (animalData.behavior) formData.append('behavior', animalData.behavior);

    // Add detailed fields
    if (animalData.medicalHistory) formData.append('medicalHistory', animalData.medicalHistory);
    if (animalData.personalityTraits) formData.append('personalityTraits', animalData.personalityTraits);
    if (animalData.habits) formData.append('habits', animalData.habits);

    // Add files
    if (animalData.medicalFile) formData.append('medicalFile', animalData.medicalFile);
    if (animalData.photo) formData.append('photo', animalData.photo);
    if (animalData.photoUrl) formData.append('photoUrl', animalData.photoUrl);

    return this.http.put(`${this.baseUrl}/${animalId}`, formData);
  }

  deleteAnimal(animalId: number): Observable<any> {
    return this.http.delete(`${this.baseUrl}/${animalId}`);
  }

  updateAnimalStatus(animalId: number, status: string): Observable<any> {
    return this.http.patch(`${this.baseUrl}/${animalId}/status`, { status });
  }

  getMascotasByShelterId(shelterId: number): Observable<any> {
    return this.http.get(`${this.baseUrl}/shelter/${shelterId}`);
  }
}
