import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule, FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-register-shelter',
  standalone: true,
  templateUrl: './register-form-pet-house.component.html',
  styleUrls: ['./register-form-pet-house.component.css'],
  imports: [CommonModule, ReactiveFormsModule, FormsModule]
})
export class RegisterShelterComponent {
  registerForm: FormGroup;
  selectedFiles: File[] = [];

  constructor(private fb: FormBuilder) {
    this.registerForm = this.fb.group({
      legalName: ['', Validators.required],
      address: ['', Validators.required],
      acceptTerms: [false, Validators.requiredTrue]
    });
  }

  onFileChange(event: any) {
    this.selectedFiles = [];
    if (event.target.files && event.target.files.length > 0) {
      for (let i = 0; i < event.target.files.length; i++) {
        this.selectedFiles.push(event.target.files[i]);
      }
    }
  }

  onSubmit() {
    if (this.registerForm.valid) {
      const formData = new FormData();
      
      formData.append('legalName', this.registerForm.get('legalName')?.value);
      formData.append('address', this.registerForm.get('address')?.value);
      
      this.selectedFiles.forEach((file, index) => {
        formData.append(`document${index}`, file);
      });

      console.log('Formulario enviado:', formData);
    }
  }

  openTerms() {
    alert('Aquí puedes mostrar los términos y condiciones');
  }

  navigateToLogin() {
    // this.router.navigate(['/login']);
  }
}