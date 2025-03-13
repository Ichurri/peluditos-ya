// components/register/register.component.ts
import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { AuthService } from '../../services/auth.service'; // Importa el servicio

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {
  registerForm: FormGroup;
  router: any;

  constructor(private fb: FormBuilder, private authService: AuthService) {
    this.registerForm = this.fb.group({
      name: ['', Validators.required], // ✅ Coincide con el HTML
      email: ['', [Validators.required, Validators.email]],
      phone: ['', [Validators.required, Validators.pattern('[0-9]{7,15}')]],
      password: ['', [Validators.required, Validators.minLength(6)]],
      location: ['', Validators.required], // ✅ Agregado para que coincida con tu DB
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

      // this.authService.register(formData).subscribe({
      //   next: (response) => {
      //     console.log('Registro exitoso:', response);
      //     alert(response); // Muestra el mensaje de éxito
      //   },
      //   error: (error) => {
      //     console.error('Error en el registro:', error);
      //     alert('Error al registrar usuario');
      //   }
      // });
      this.authService.register(formData).subscribe({
        next: (response) => {
          console.log('Registro exitoso:', response);
          alert(response);
          this.router.navigate(['/login']);
          // Redirigir a otra página aquí si es necesario
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
    // You can implement the logic to show terms and conditions here
    console.log('Terms and conditions clicked');
  }

  navigateToLogin() {
    this.router.navigate(['/login']);

  }
}
