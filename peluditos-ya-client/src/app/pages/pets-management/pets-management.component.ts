import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AnimalService } from '../../services/auth.animal.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-pets-management',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './pets-management.component.html',
  styleUrls: ['./pets-management.component.css']
})
export class PetsManagementComponent implements OnInit {
  mascotas: any[] = [];
  mascotasFiltradas: any[] = [];
  searchTerm: string = '';
  filterType: string = '';
  filterStatus: string = '';
  loading: boolean = false;
  error: string = '';

  constructor(
    private animalService: AnimalService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.loadMascotas();
  }

  loadMascotas(): void {
    this.loading = true;
    this.error = '';
    
    this.animalService.obtenerMascotas().subscribe({
      next: (data) => {
        this.mascotas = data;
        this.mascotasFiltradas = data;
        this.loading = false;
      },
      error: (error) => {
        this.error = 'Error al cargar las mascotas';
        this.loading = false;
        console.error('Error loading pets:', error);
      }
    });
  }

  filterMascotas(): void {
    this.mascotasFiltradas = this.mascotas.filter(mascota => {
      const matchesSearch = this.searchTerm === '' || 
        mascota.name.toLowerCase().includes(this.searchTerm.toLowerCase()) ||
        mascota.breed.toLowerCase().includes(this.searchTerm.toLowerCase());
      
      const matchesType = this.filterType === '' || 
        mascota.animal.toLowerCase() === this.filterType.toLowerCase();
      
      const matchesStatus = this.filterStatus === '' || 
        mascota.status?.toLowerCase() === this.filterStatus.toLowerCase();

      return matchesSearch && matchesType && matchesStatus;
    });
  }

  onSearchChange(): void {
    this.filterMascotas();
  }

  onFilterChange(): void {
    this.filterMascotas();
  }

  editMascota(id: number): void {
    this.router.navigate(['/edit-animal', id]);
  }

  viewProfile(id: number): void {
    this.router.navigate(['/pet-profile', id]);
  }

  deleteMascota(mascota: any): void {
    if (confirm(`¿Estás seguro de que quieres eliminar a ${mascota.name}?`)) {
      // TODO: Implementar método de eliminación en el servicio
      console.log('Eliminar mascota:', mascota.id);
    }
  }

  updateStatus(mascota: any, newStatus: string): void {
    if (confirm(`¿Cambiar el estado de ${mascota.name} a ${newStatus}?`)) {
      // TODO: Implementar método de actualización de estado en el servicio
      console.log('Actualizar estado:', mascota.id, newStatus);
    }
  }

  getStatusText(status: string): string {
    const statusMap: { [key: string]: string } = {
      'disponible': 'Disponible',
      'adoptado': 'Adoptado',
      'en_proceso': 'En Proceso',
      'reservado': 'Reservado'
    };
    return statusMap[status] || 'Disponible';
  }
}
