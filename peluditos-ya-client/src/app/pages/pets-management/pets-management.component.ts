import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AnimalService } from '../../services/auth.animal.service';
import { Router } from '@angular/router';
import { environment } from '../../../environments/environment';

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
  operationInProgress: boolean = false;

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

  refreshData(): void {
    this.loadMascotas();
  }

  editMascota(id: number): void {
    this.router.navigate(['/edit-animal', id]);
  }

  viewProfile(id: number): void {
    this.router.navigate(['/animal-profile', id]);
  }

  deleteMascota(mascota: any): void {
    if (this.operationInProgress) return;
    
    const confirmMessage = `⚠️ ELIMINAR MASCOTA
    
Nombre: ${mascota.name}
Tipo: ${mascota.animal}
Raza: ${mascota.breed}

¿Estás completamente seguro de que quieres eliminar esta mascota?
Esta acción NO se puede deshacer y eliminará permanentemente todos los datos asociados.`;

    if (confirm(confirmMessage)) {
      this.operationInProgress = true;
      
      this.animalService.deleteAnimal(mascota.id).subscribe({
        next: (response) => {
          console.log('Mascota eliminada exitosamente:', response);
          // Remover la mascota de la lista local
          this.mascotas = this.mascotas.filter(m => m.id !== mascota.id);
          this.filterMascotas();
          this.showSuccessMessage(`✅ ${mascota.name} ha sido eliminado exitosamente.`);
          this.operationInProgress = false;
        },
        error: (error) => {
          console.error('Error al eliminar mascota:', error);
          this.showErrorMessage('❌ Error al eliminar la mascota. Por favor, inténtalo de nuevo.');
          this.operationInProgress = false;
        }
      });
    }
  }

  updateStatus(mascota: any, newStatus: string): void {
    if (this.operationInProgress) return;
    
    const statusText = this.getStatusText(newStatus);
    const confirmMessage = `🔄 CAMBIAR ESTADO
    
Mascota: ${mascota.name}
Estado actual: ${this.getStatusText(mascota.status || 'disponible')}
Nuevo estado: ${statusText}

¿Confirmar el cambio de estado?`;

    if (confirm(confirmMessage)) {
      this.operationInProgress = true;
      
      this.animalService.updateAnimalStatus(mascota.id, newStatus).subscribe({
        next: (response) => {
          console.log('Estado actualizado exitosamente:', response);
          // Actualizar el estado en la lista local
          const index = this.mascotas.findIndex(m => m.id === mascota.id);
          if (index !== -1) {
            this.mascotas[index].status = newStatus;
            this.filterMascotas();
          }
          this.showSuccessMessage(`✅ El estado de ${mascota.name} ha sido cambiado a ${statusText}.`);
          this.operationInProgress = false;
        },
        error: (error) => {
          console.error('Error al actualizar estado:', error);
          this.showErrorMessage('❌ Error al actualizar el estado. Por favor, inténtalo de nuevo.');
          this.operationInProgress = false;
        }
      });
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

  private showSuccessMessage(message: string): void {
    alert(message);
  }

  private showErrorMessage(message: string): void {
    alert(message);
  }

  onImageError(event: any): void {
    event.target.src = '/assets/images/default-pet.svg';
  }

  getImageUrl(photoPath: string | null): string {
    if (!photoPath) {
      return '/assets/images/default-pet.svg';
    }
    
    // If photoPath is already a full URL (starts with http), return as is
    if (photoPath.startsWith('http')) {
      return photoPath;
    }
    
    // If photoPath starts with /api, construct full URL with backend base
    if (photoPath.startsWith('/api')) {
      return `${environment.apiBaseUrl.replace('/api', '')}${photoPath}`;
    }
    
    // If it's a legacy absolute path, use default image
    if (photoPath.startsWith('/') && !photoPath.startsWith('/api')) {
      return '/assets/images/default-pet.svg';
    }
    
    // Default fallback
    return '/assets/images/default-pet.svg';
  }
}
