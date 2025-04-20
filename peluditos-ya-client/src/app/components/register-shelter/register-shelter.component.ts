import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { AuthShelterService } from '../../services';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register-shelter',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './register-shelter.component.html',
  styleUrls: ['./register-shelter.component.css']
})
export class RegisterShelterComponent {
  registerForm: FormGroup;

  constructor(private fb: FormBuilder, private authService: AuthShelterService, private router: Router) {
    this.registerForm = this.fb.group({
      description: ['', [Validators.required, Validators.minLength(10)]],
      documentNumber: ['', [Validators.required, Validators.pattern('[0-9]{7,15}')]],
      shelterAddress: ['', Validators.required],
      name: ['', Validators.required], // Coincide con el HTML
      phone: ['', [Validators.required, Validators.pattern('[0-9]{7,15}')]],
      city: ['', Validators.required], // Agregado para que coincida con tu DB
      isAdmin: ['', false],
      acceptTerms: [false, Validators.requiredTrue]
    });
    
  }

  onSubmit() {
    if (this.registerForm.valid) {
      const userId = 1;
      console.log('User ID:', userId); // cambiar esto para poder obtener el user id desde el local storage
  
      const payload = {
        userId,
        description: this.registerForm.value.description
      };

      console.log('Enviando datos:', payload);
  
      this.authService.submitShelterRequest(payload).subscribe({
        next: (response) => {
          console.log('Solicitud enviada:', response);
          alert('Tu solicitud fue enviada con éxito');
          this.registerForm.reset();
        },
        error: (error) => {
          console.error('Error al enviar solicitud:', error);
          alert('Error al enviar la solicitud');
        }
      });
    } else {
      Object.values(this.registerForm.controls).forEach(control => {
        control.markAsTouched();
      });
    }
  }
  

  openTerms() {
    console.log('Terms and conditions clicked');
  }

  navigateToLogin() {
    this.router.navigate(['/login']);
  }
}