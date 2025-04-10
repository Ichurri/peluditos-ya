import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { AuthAdopterService } from '../../services';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  loginForm: FormGroup;
  isSubmitting: boolean = false;

  constructor(
    private fb: FormBuilder,
    private authService: AuthAdopterService,
    private router: Router
  ) {
    this.loginForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required]
    });
  }

  onSubmit() {
    if (this.loginForm.invalid) return;
  
    this.isSubmitting = true;
    console.log('Formulario enviado:', this.loginForm.value);
  
    this.authService.login(this.loginForm.value).subscribe({
      next: (response) => {
        console.log('Respuesta del backend:', response);
        this.isSubmitting = false;
        alert(response.message);
        localStorage.setItem('isAdmin', response.admin.toString());
        localStorage.setItem('userEmail', this.loginForm.value.email);
  
        this.router.navigate([response.admin ? '/admin-dashboard' : '/dashboard']);
      },
      error: (error) => {
        this.isSubmitting = false;
        console.error('Error en login:', error);
        alert(error.error?.message || 'Credenciales incorrectas');
      }
    });
  }

  navigateToRegister() {
    this.router.navigate(['/register-adopter']);
  }

  navigateToForgotPassword() {
    this.router.navigate(['/forgot-password']);
  }

  navigateToHome() {
    this.router.navigate(['/']);
  }
}
