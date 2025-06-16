import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AnimalService } from '../../services/auth.animal.service';
import { AuthShelterService } from '../../services/auth.shelter.service';
import { CommonModule } from '@angular/common';
import * as QRCode from 'qrcode';

@Component({
  selector: 'app-pet-profile',
  templateUrl: './pet-profile.component.html',
  styleUrls: ['./pet-profile.component.css'],
  standalone: true,
  imports: [CommonModule]
})
export class PetProfileComponent implements OnInit {
  petId!: number;
  pet: any;
  shelter: any = null;
  loading = true;
  error = false;
  asignando = false;
  qrCodeImage: string = '';
  canEdit = false;
  defaultImage = 'https://files.lafm.com.co/assets/public/styles/twitter/public/2023-08/murio_cheems_el_perrito_de_los_meme.jpg.webp?VersionId=dHwATkyc2gQxvSwNeWXyOFUXPzaF3bbQ&itok=MmkIcD6M'; 

  constructor(
    private readonly route: ActivatedRoute,
    private readonly router: Router,
    private readonly animalService: AnimalService,
    private readonly shelterService: AuthShelterService
  ) {}

  ngOnInit(): void {
    this.petId = Number(this.route.snapshot.paramMap.get('id'));
    this.getPetProfile();
  }

  getPetProfile(): void {
  this.animalService.getAnimalProfile(this.petId).subscribe({
    next: (data) => {
      this.pet = data;
      console.log('Datos de la mascota:', this.pet); 
      this.checkEditPermissions();
      if (this.pet.shelterId) {
        this.shelterService.getShelterById(this.pet.shelterId).subscribe({
          next: (shelterData) => {
            console.log('Datos del shelter:', shelterData); // <-- Aquí imprime los datos
            this.shelter = shelterData;
          },
          error: (err) => {
            console.error('Error al cargar el refugio', err);
          }
        });
      }
      this.loading = false;
      if (!this.pet.sponsor) {
        this.generateQRCode();
      }
    },
    error: (err) => {
      console.error('Error al cargar el perfil del animal', err);
      this.error = true;
      this.loading = false;
    }
  });
}

  asignarPadrino(): void {
    const userId = localStorage.getItem('userId');
    if (!userId) {
      alert('Debes iniciar sesión para apadrinar.');
      return;
    }

    this.asignando = true;
    this.animalService.asignarPadrino(this.petId, +userId).subscribe({
      next: (data) => {
        this.pet = data;
        this.asignando = false;
        alert('¡Gracias por apadrinar a ' + this.pet.name + '!');
      },
      error: (err) => {
        console.error('Error al asignar padrino', err);
        this.asignando = false;
        alert('Hubo un error al intentar apadrinar.');
      }
    });
  }

  generateSponsorUrl(animalId: number): string {
    const baseUrl = window.location.origin;
    return `${baseUrl}/apadrinar/${animalId}`;
  }

  generateQRCode(): void {
    const url = this.generateSponsorUrl(this.petId);
    QRCode.toDataURL(url)
      .then(dataUrl => {
        this.qrCodeImage = dataUrl;
      })
      .catch(err => {
        console.error('Error al generar el código QR', err);
      });
  }

  editAnimal(): void {
    this.router.navigate(['/edit-animal', this.petId]);
  }

  checkEditPermissions(): void {
    // Implementar lógica básica de autorización
    // Por ahora, permitir edición si el usuario está logueado
    // En una implementación real, verificaríamos si el usuario es admin
    // o si es el refugio propietario del animal
    const userId = localStorage.getItem('userId');
    const isAdmin = localStorage.getItem('isAdmin') === 'true';
    
    this.canEdit = !!userId && (isAdmin || this.isOwnerShelter());
  }

  private isOwnerShelter(): boolean {
    // Verificar si el usuario actual es del refugio propietario
    // En una implementación real, esto se haría con datos del backend
    const currentShelterId = localStorage.getItem('shelterId');
    return !!(currentShelterId && this.pet?.shelterId?.toString() === currentShelterId);
  }
}
