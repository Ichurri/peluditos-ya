import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AnimalService } from '../../services/auth.animal.service';
import { AuthAdopterService } from '../../services/auth.adopter.service';
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

  // Control de acceso
  currentUser: any = null;
  isAdmin: boolean = false;
  userShelterId: number | null = null;

  constructor(
    private animalService: AnimalService,
    private authService: AuthAdopterService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.initializeUserInfo();
    this.loadMascotas();
  }

  private initializeUserInfo(): void {
    this.currentUser = this.authService.getCurrentUser();
    this.isAdmin = this.authService.isAdminUser();
    this.userShelterId = this.authService.getShelterId();
    
    console.log('User info:', {
      currentUser: this.currentUser,
      isAdmin: this.isAdmin,
      userShelterId: this.userShelterId
    });
  }

  loadMascotas(): void {
    this.loading = true;
    this.error = '';
    
    this.animalService.obtenerMascotas().subscribe({
      next: (data) => {
        // Filtrar mascotas por refugio si el usuario no es admin
        if (!this.isAdmin && this.userShelterId) {
          this.mascotas = data.filter((mascota: any) => mascota.shelterId === this.userShelterId);
        } else {
          this.mascotas = data;
        }
        
        this.mascotasFiltradas = this.mascotas;
        this.loading = false;
        this.filterMascotas(); // Aplicar filtros después de cargar
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
        mascota.animalType === this.filterType;
      
      const matchesStatus = this.filterStatus === '' || 
        this.normalizeStatus(mascota.status) === this.normalizeStatus(this.filterStatus);

      return matchesSearch && matchesType && matchesStatus;
    });
  }

  normalizeStatus(status: string): string {
    if (!status) return 'disponible';
    
    // Mapear estados del backend a estados del frontend para comparación
    const backendToFrontend: { [key: string]: string } = {
      'AVAILABLE': 'disponible',
      'ADOPTED': 'adoptado',
      'IN_PROCESS': 'en_proceso',
      'RESERVED': 'reservado'
    };
    
    return backendToFrontend[status.toUpperCase()] || status.toLowerCase();
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

  // Verificar si el usuario puede realizar acciones sobre una mascota específica
  canManagePet(mascota: any): boolean {
    // Los administradores pueden gestionar todas las mascotas
    if (this.isAdmin) {
      return true;
    }
    
    // Los encargados de refugio solo pueden gestionar mascotas de su refugio
    if (this.userShelterId && mascota.shelterId === this.userShelterId) {
      return true;
    }
    
    return false;
  }

  // Mostrar información de permisos en la interfaz
  getPermissionMessage(mascota: any): string {
    if (!this.canManagePet(mascota)) {
      return 'Solo puedes gestionar mascotas de tu refugio';
    }
    return '';
  }

  deleteMascota(mascota: any): void {
    if (this.operationInProgress) return;
    
    // Verificar permisos antes de proceder
    if (!this.canManagePet(mascota)) {
      alert('❌ No tienes permisos para eliminar esta mascota. Solo puedes gestionar mascotas de tu refugio.');
      return;
    }
    
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
    
    // Verificar permisos antes de proceder
    if (!this.canManagePet(mascota)) {
      alert('❌ No tienes permisos para actualizar el estado de esta mascota. Solo puedes gestionar mascotas de tu refugio.');
      return;
    }
    
    const statusText = this.getStatusText(newStatus);
    const confirmMessage = `🔄 CAMBIAR ESTADO
    
Mascota: ${mascota.name}
Estado actual: ${this.getStatusText(mascota.status || 'disponible')}
Nuevo estado: ${statusText}

¿Confirmar el cambio de estado?`;

    if (confirm(confirmMessage)) {
      this.operationInProgress = true;
      
      // Convertir el estado al formato requerido por la base de datos
      const backendStatus = this.mapStatusToBackend(newStatus);
      
      this.animalService.updateAnimalStatus(mascota.id, backendStatus).subscribe({
        next: (response) => {
          console.log('Estado actualizado exitosamente:', response);
          // Actualizar el estado en la lista local con el valor devuelto por el backend
          const index = this.mascotas.findIndex(m => m.id === mascota.id);
          if (index !== -1) {
            this.mascotas[index].status = response.status;
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
      // Valores del frontend (español, minúsculas)
      'disponible': 'Disponible',
      'adoptado': 'Adoptado',
      'en_proceso': 'En Proceso',
      'reservado': 'Reservado',
      // Valores del backend (inglés, mayúsculas)
      'AVAILABLE': 'Disponible',
      'ADOPTED': 'Adoptado',
      'IN_PROCESS': 'En Proceso',
      'RESERVED': 'Reservado'
    };
    return statusMap[status] || 'Disponible';
  }

  mapStatusToBackend(frontendStatus: string): string {
    const statusMap: { [key: string]: string } = {
      'disponible': 'AVAILABLE',
      'adoptado': 'ADOPTED',
      'en_proceso': 'IN_PROCESS',
      'reservado': 'RESERVED'
    };
    return statusMap[frontendStatus] || 'AVAILABLE';
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

  getAnimalTypeText(animalType: string): string {
    if (!animalType) {
      return 'N/A';
    }
    
    const typeMap: { [key: string]: string } = {
      'CAT': 'Gato',
      'DOG': 'Perro'
    };
    
    return typeMap[animalType.toUpperCase()] || animalType;
  }
}
