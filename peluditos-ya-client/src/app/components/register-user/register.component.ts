// components/register/register.component.ts
import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { AuthService } from '../../services/auth.service'; // Importa el servicio
import { Router } from '@angular/router'; // Para redirigir después del registro

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {
  registerForm: FormGroup;

  constructor(
    private fb: FormBuilder,
    private authService: AuthService, // Inyecta AuthService
    private router: Router // Inyecta Router para redirigir después del registro
  ) {
    this.registerForm = this.fb.group({
      fullName: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      phone: ['', Validators.required],
      password: ['', [Validators.required, Validators.minLength(6)]],
      acceptTerms: [false, Validators.requiredTrue]
    });
  }

  onSubmit() {
    if (this.registerForm.valid) {
      console.log('Form submitted:', this.registerForm.value);
      // Llama al servicio de registro
      this.authService.register(this.registerForm.value).subscribe({
        next: (response) => {
          console.log('Registro exitoso', response);
          // Redirige a la página de inicio de sesión después del registro
          this.router.navigate(['/login']);
        },
        error: (error) => {
          console.error('Error al registrar usuario', error);
        }
      });
    } else {
      // Marca todos los campos como tocados para mostrar los errores
      Object.keys(this.registerForm.controls).forEach((key) => {
        const control = this.registerForm.get(key);
        control?.markAsTouched();
      });
    }
  }

  openTerms() {
    console.log('Terms and conditions clicked');
  }

  navigateToLogin() {
    console.log('Navigate to login page');
  }
}
