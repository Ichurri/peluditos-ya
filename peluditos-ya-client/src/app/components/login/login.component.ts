import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { AuthService } from '../../services/auth.service';
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
  isSubmitting: boolean = false; // Añade esta variable

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router
  ) {
    this.loginForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required]
    });
  }

  onSubmit() {
    if (this.loginForm.invalid) return;

    this.isSubmitting = true; // Activar spinner

    this.authService.login(this.loginForm.value).subscribe({
      next: (response) => {
        this.isSubmitting = false; // Desactivar spinner
        alert('¡Bienvenido!');
        this.router.navigate(['/dashboard']);
      },
      error: (error) => {
        this.isSubmitting = false; // Desactivar spinner en caso de error
        alert(error.error || 'Credenciales incorrectas');
      }
    });
  }

  navigateToRegister() {
    this.router.navigate(['/register']);
  }

  navigateToForgotPassword() {
    this.router.navigate(['/forgot-password']);
  }

  navigateToHome() {
    this.router.navigate(['/']);
  }
}