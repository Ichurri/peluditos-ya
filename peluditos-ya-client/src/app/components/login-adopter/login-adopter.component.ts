import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './login-adopter.component.html',
  styleUrls: ['./login-adopter.component.css']
})
export class LoginAdopterComponent {
  loginForm: FormGroup;
  isSubmitting: boolean = false;

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

    this.isSubmitting = true;

    this.authService.login(this.loginForm.value).subscribe({
      next: (response) => {
        this.isSubmitting = false;
        alert(response.message);
        localStorage.setItem('isAdmin', response.admin.toString());

        localStorage.setItem('userEmail', this.loginForm.value.email);


        if (response.admin) {
          this.router.navigate(['/admin-dashboard']);
        } else {
          this.router.navigate(['/dashboard']);
        }
      },
      error: (error) => {
        this.isSubmitting = false;
        alert(error.error.message || 'Credenciales incorrectas');
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
