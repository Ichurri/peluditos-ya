import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { AnimalService } from '../../services/auth.animal.service';

@Component({
  selector: 'app-edit-animal',
  templateUrl: './edit-animal.component.html',
  styleUrls: ['./edit-animal.component.css'],
  standalone: true,
  imports: [CommonModule, FormsModule]
})
export class EditAnimalComponent implements OnInit {
  animalId!: number;
  animal: any = {};
  loading = true;
  saving = false;
  error = '';
  success = '';
  selectedMedicalFile: File | null = null;
  selectedPhoto: File | null = null;

  animalTypes = ['CAT', 'DOG'];
  behaviors = ['FRIENDLY', 'SHY', 'AGGRESSIVE', 'QUIET'];

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private animalService: AnimalService
  ) {}

  ngOnInit(): void {
    this.animalId = Number(this.route.snapshot.paramMap.get('id'));
    this.loadAnimalData();
  }

  loadAnimalData(): void {
    this.animalService.getAnimalProfile(this.animalId).subscribe({
      next: (data) => {
        this.animal = data;
        this.loading = false;
      },
      error: (err) => {
        console.error('Error al cargar datos del animal:', err);
        this.error = 'Error al cargar los datos del animal';
        this.loading = false;
      }
    });
  }

  onMedicalFileSelected(event: any): void {
    const file = event.target.files[0];
    if (file && file.type === 'application/pdf') {
      this.selectedMedicalFile = file;
    } else {
      this.error = 'Por favor selecciona un archivo PDF válido para el historial médico';
    }
  }

  onPhotoSelected(event: any): void {
    const file = event.target.files[0];
    if (file && file.type.startsWith('image/')) {
      this.selectedPhoto = file;
    } else {
      this.error = 'Por favor selecciona una imagen válida';
    }
  }

  updateAnimal(): void {
    if (!this.validateForm()) {
      return;
    }

    this.saving = true;
    this.error = '';
    this.success = '';

    const updateData = {
      name: this.animal.name,
      animal: this.animal.animalType,
      breed: this.animal.breed,
      age: this.animal.age,
      behavior: this.animal.behavior,
      medicalHistory: this.animal.medicalHistory,
      personalityTraits: this.animal.personalityTraits,
      habits: this.animal.habits,
      photoUrl: this.animal.photoPath,
      medicalFile: this.selectedMedicalFile,
      photo: this.selectedPhoto
    };

    this.animalService.updateAnimal(this.animalId, updateData).subscribe({
      next: (response) => {
        this.success = 'Animal actualizado exitosamente';
        this.saving = false;
        setTimeout(() => {
          this.router.navigate(['/animal-profile', this.animalId]);
        }, 2000);
      },
      error: (err) => {
        console.error('Error al actualizar animal:', err);
        this.error = err.error?.message || 'Error al actualizar el animal';
        this.saving = false;
      }
    });
  }

  validateForm(): boolean {
    if (!this.animal.name?.trim()) {
      this.error = 'El nombre es requerido';
      return false;
    }
    
    if (!this.animal.animalType) {
      this.error = 'El tipo de animal es requerido';
      return false;
    }
    
    if (!this.animal.breed?.trim()) {
      this.error = 'La raza es requerida';
      return false;
    }
    
    if (!this.animal.age || this.animal.age <= 0) {
      this.error = 'La edad debe ser mayor a 0';
      return false;
    }
    
    if (!this.animal.behavior) {
      this.error = 'El comportamiento es requerido';
      return false;
    }

    return true;
  }

  cancel(): void {
    this.router.navigate(['/animal-profile', this.animalId]);
  }
}
