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
      name: ['', Validators.required],
      documentNumber: ['', Validators.required],
      shelterName: ['', Validators.required],      
      email: ['', [Validators.required, Validators.email]],
      phone: ['', [Validators.required, Validators.pattern('[0-9]{7,15}')]],
      city: ['', Validators.required], // Ciudad
      shelterAddress: ['', Validators.required],
      password: ['', [Validators.required, Validators.minLength(6)]],
      isAdmin: ['', false],
      acceptTerms: [false, Validators.requiredTrue]
    });
  }

  onSubmit() {
    if (this.registerForm.valid) {
      const formData = {
        ...this.registerForm.value,
        acceptTerms: undefined // No lo enviamos al backend
      };

      console.log('Enviando datos del refugio:', formData);

      this.authService.register(formData).subscribe({
        next: (response) => {
          console.log('Registro de refugio exitoso:', response);
          alert(response);
        },
        error: (error) => {
          console.error('Error en el registro del refugio:', error);
          alert(error.error.text || 'Error al registrar refugio');
        }
      });
    } else {
      Object.keys(this.registerForm.controls).forEach(key => {
        const control = this.registerForm.get(key);
        control?.markAsTouched();
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