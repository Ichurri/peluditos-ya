import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthShelterService } from '../../services/auth.shelter.service';

@Component({
  selector: 'app-edit-shelter',
  templateUrl: './edit-shelter.component.html',
  styleUrls: ['./edit-shelter.component.css'],
  standalone: true,
  imports: [CommonModule, FormsModule]
})
export class EditShelterComponent implements OnInit {
  shelterId!: number;
  shelter: any = {};
  loading = true;
  saving = false;
  error = '';
  success = '';
  selectedDocumentFile: File | null = null;

  constructor(
    private readonly route: ActivatedRoute,
    private readonly router: Router,
    private readonly shelterService: AuthShelterService
  ) {}

  ngOnInit(): void {
    this.shelterId = Number(this.route.snapshot.paramMap.get('id'));
    this.loadShelterData();
  }

  loadShelterData(): void {
    this.shelterService.getShelterById(this.shelterId).subscribe({
      next: (data) => {
        this.shelter = data;
        this.loading = false;
      },
      error: (err) => {
        console.error('Error al cargar datos del refugio:', err);
        this.error = 'Error al cargar los datos del refugio';
        this.loading = false;
      }
    });
  }

  onDocumentFileSelected(event: any): void {
    const file = event.target.files[0];
    if (file && file.type === 'application/pdf') {
      this.selectedDocumentFile = file;
    } else {
      this.error = 'Por favor selecciona un archivo PDF válido para el documento';
    }
  }

  updateShelter(): void {
    if (!this.validateForm()) {
      return;
    }

    this.saving = true;
    this.error = '';
    this.success = '';

    const updateData = {
      name: this.shelter.name,
      email: this.shelter.email,
      city: this.shelter.city,
      phone: this.shelter.phone,
      shelterName: this.shelter.shelterName,
      shelterAddress: this.shelter.shelterAddress,
      description: this.shelter.description,
      latitude: this.shelter.latitude,
      longitude: this.shelter.longitude,
      documentFile: this.selectedDocumentFile
    };

    this.shelterService.updateShelter(this.shelterId, updateData).subscribe({
      next: (response) => {
        this.success = 'Refugio actualizado exitosamente';
        this.saving = false;
        setTimeout(() => {
          this.router.navigate(['/shelter-profile', this.shelterId]);
        }, 2000);
      },
      error: (err) => {
        console.error('Error al actualizar refugio:', err);
        this.error = err.error?.message ?? 'Error al actualizar el refugio';
        this.saving = false;
      }
    });
  }

  validateForm(): boolean {
    if (!this.shelter.name?.trim()) {
      this.error = 'El nombre del contacto es requerido';
      return false;
    }
    
    if (!this.shelter.email?.trim()) {
      this.error = 'El email es requerido';
      return false;
    }
    
    if (!this.shelter.shelterName?.trim()) {
      this.error = 'El nombre del refugio es requerido';
      return false;
    }
    
    if (!this.shelter.shelterAddress?.trim()) {
      this.error = 'La dirección del refugio es requerida';
      return false;
    }
    
    if (!this.shelter.city?.trim()) {
      this.error = 'La ciudad es requerida';
      return false;
    }
    
    if (!this.shelter.phone?.trim()) {
      this.error = 'El teléfono es requerido';
      return false;
    }

    // Validar formato de email
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailRegex.test(this.shelter.email)) {
      this.error = 'El formato del email no es válido';
      return false;
    }

    // Validar formato de teléfono
    const phoneRegex = /^\d{7,15}$/;
    if (!phoneRegex.test(this.shelter.phone)) {
      this.error = 'El teléfono debe contener entre 7 y 15 dígitos';
      return false;
    }

    return true;
  }

  cancel(): void {
    this.router.navigate(['/shelter-profile', this.shelterId]);
  }
}
