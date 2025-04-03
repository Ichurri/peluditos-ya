import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { AuthAdopterService } from '../../services';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register-adopter',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './register-adopter.component.html',
  styleUrls: ['./register-adopter.component.css']
})
export class RegisterAdopterComponent {
  registerForm: FormGroup;

  constructor(private fb: FormBuilder, private authService: AuthAdopterService, private router: Router) {
    this.registerForm = this.fb.group({
      name: ['', Validators.required], // Coincide con el HTML
      email: ['', [Validators.required, Validators.email]],
      phone: ['', [Validators.required, Validators.pattern('[0-9]{7,15}')]],
      password: ['', [Validators.required, Validators.minLength(6)]],
      city: ['', Validators.required], // Agregado para que coincida con tu DB
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

      console.log('Enviando datos:', formData);

      this.authService.register(formData).subscribe({
        next: (response) => {
          console.log('Registro exitoso:', response);
          alert(response);
        },
        error: (error) => {
          console.error('Error en el registro:', error);
          alert(error.error.text || 'Error al registrar usuario');  // <- Usar error.text
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
    this.router.navigate(['/login-adopter']);
  }
}
