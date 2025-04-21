import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common'; 
import { FormsModule } from '@angular/forms';   
import { HttpClient } from '@angular/common/http';
import { AuthShelterService } from '../../services';

@Component({
  selector: 'app-admin-shelters',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './admin-page.component.html',
  styleUrls: ['./admin-page.component.css']
})
export class AdminSheltersComponent implements OnInit {
  searchTerm = '';
  casasHogar: any[] = [];
  pendingShelters: any[] = [];

  constructor(private AuthShelterService: AuthShelterService, private http: HttpClient) {}

  ngOnInit(): void {
    this.loadShelters();
    this.loadPendingShelters();
  }

  loadShelters(): void {
    this.AuthShelterService.getApprovedShelters().subscribe(
      (data) => {
        this.casasHogar = data;
      },
      (error) => {
        console.error('Error al cargar refugios:', error);
      }
    );
  }

  loadPendingShelters(): void {
    this.getPendingShelters().subscribe(
      (data) => {
        this.pendingShelters = data;
        console.log('Pending shelters:', this.pendingShelters);
      },
      (error) => {
        console.error('Error al cargar solicitudes pendientes:', error);
      }
    );
  }

  getCasasFiltradas() {
    return this.casasHogar.filter(casa =>
      this.searchTerm === '' || 
      casa.shelterName.toLowerCase().includes(this.searchTerm.toLowerCase())
    );
  }

  eliminarCasa(id: number): void {
    this.AuthShelterService.deleteShelter(id).subscribe(
      () => {
        this.casasHogar = this.casasHogar.filter(casa => casa.id !== id);
      },
      (error) => {
        console.error('Error al eliminar refugio:', error);
      }
    );
  }

  getPendingShelters() {
    return this.http.get<any[]>('http://localhost:8080/api/shelter-requests/pending');
  }
  
  

  approveShelter(id: number): void {
    this.http.put(`http://localhost:8080/api/shelter-requests/${id}/status?status=APPROVED`, {}).subscribe(
      () => {
        this.pendingShelters = this.pendingShelters.filter(s => s.id !== id);
        this.loadShelters(); // Actualiza lista de aprobados
      },
      (error) => {
        console.error('Error al aprobar solicitud:', error);
      }
    );
  }

  rejectShelter(id: number): void {
    this.http.put(`http://localhost:8080/api/shelter-requests/${id}/status?status=REJECTED`, {}).subscribe(
      () => {
        this.pendingShelters = this.pendingShelters.filter(s => s.id !== id);
      },
      (error) => {
        console.error('Error al rechazar solicitud:', error);
      }
    );
  }
}
