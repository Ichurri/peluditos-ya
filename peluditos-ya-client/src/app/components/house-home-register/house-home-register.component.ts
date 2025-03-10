// components/house-home-register/house-home-register.component.ts
import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';

@Component({
  selector: 'app-house-home-register',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './house-home-register.component.html',
  styleUrls: ['./house-home-register.component.css']
})
export class HouseHomeRegisterComponent {
  registerForm: FormGroup;

  constructor(private fb: FormBuilder) {
    this.registerForm = this.fb.group({
      casaHogarName: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      phone: ['', Validators.required],
      address: ['', Validators.required],
      password: ['', [Validators.required, Validators.minLength(6)]],
      acceptTerms: [false, Validators.requiredTrue]
    });
  }

  onSubmit() {
    if (this.registerForm.valid) {
      console.log('Casa Hogar Form submitted:', this.registerForm.value);
      // Here you would typically call your authentication service
    } else {
      // Mark all fields as touched to trigger validation display
      Object.keys(this.registerForm.controls).forEach(key => {
        const control = this.registerForm.get(key);
        control?.markAsTouched();
      });
    }
  }

  openTerms() {
    // Logic to show terms and conditions
    console.log('Terms and conditions clicked');
  }

  navigateToLogin() {
    // Logic to navigate to login page
    console.log('Navigate to login page');
  }
}