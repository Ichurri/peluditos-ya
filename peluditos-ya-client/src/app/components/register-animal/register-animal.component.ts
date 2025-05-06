import { Component } from '@angular/core';
import { AnimalService } from '../../services/auth.animal.service';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-register-animal',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './register-animal.component.html',
  styleUrls: ['./register-animal.component.css']
})
export class RegisterAnimalComponent {
  nuevaMascota: any = {
    name: '',
    animal: '',
    breed: '',
    age: '',
    behavior: '',
    shelterId: '',
    medicalFile: null,
    photo: null
  };

  constructor(private animalService: AnimalService) {}

  onMedicalFileSelected(event: any) {
    this.nuevaMascota.medicalFile = event.target.files[0];
  }

  onPhotoSelected(event: any) {
    this.nuevaMascota.photo = event.target.files[0];
  }

  registrarMascota() {
    this.animalService.registrarAnimal(this.nuevaMascota, this.nuevaMascota.shelterId).subscribe({
      next: res => {
        console.log('Animal registrado:', res);
        alert('¡Animal registrado exitosamente!');
      },
      error: err => {
        console.error('Error:', err);
        alert('Error al registrar el animal.');
      }
    });
  }
}
