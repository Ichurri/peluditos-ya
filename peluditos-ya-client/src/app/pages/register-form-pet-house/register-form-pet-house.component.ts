import { Component } from '@angular/core';
import { FormsModule, ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { FooterComponent } from '../../components/footer/footer.component';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-register-form-pet-house',
  imports: [ FormsModule, ReactiveFormsModule, FooterComponent, CommonModule ],
  templateUrl: './register-form-pet-house.component.html',
  styleUrl: './register-form-pet-house.component.css'
})
export class RegisterFormPetHouseComponent {
  registerForm: FormGroup;
  errores: { [key: string]: string } = { identificacion: '', domicilio: '' };
  domicilioFileName: string = '';
  isSubmitting: boolean = false; 
  identificacionFileName: string = '';

  constructor(private fb: FormBuilder) {
    this.registerForm = this.fb.group({
      nombre: ['', Validators.required],
      direccion: ['', Validators.required],
      telefono: ['', [Validators.required, Validators.pattern('^[0-9]{8,15}$')]],
      identificacion: [null, Validators.required],
      domicilio: [null, Validators.required]
    });
  }

  validarArchivo(event: any, tipo: string) {
    const archivo = event.target.files[0];

    if (archivo) {
      const tiposPermitidos = ['application/pdf', 'image/jpeg', 'image/png'];
      const maxSize = 5 * 1024 * 1024; // 5MB

      if (!tiposPermitidos.includes(archivo.type)) {
        this.errores[tipo] = "Formato no permitido. Solo se aceptan PDF, JPG y PNG.";
      } else if (archivo.size > maxSize) {
        this.errores[tipo] = "El archivo excede el tamaño máximo de 5MB.";
      } else {
        this.errores[tipo] = ""; // Sin errores
        this.registerForm.patchValue({ [tipo]: archivo });
      }
    }
  }

  submitForm() {
    if (this.registerForm.valid && !this.errores['identificacion'] && !this.errores['domicilio']) {
      alert("Solicitud enviada correctamente.");
    } else {
      alert("Por favor, complete todos los campos correctamente.");
    }
  }
}