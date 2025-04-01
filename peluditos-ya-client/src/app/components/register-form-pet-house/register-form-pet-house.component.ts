import { Component } from '@angular/core';
import {
  FormsModule,
  ReactiveFormsModule,
  FormBuilder,
  FormGroup,
  Validators,
} from '@angular/forms';
import { FooterComponent } from '../../components/footer/footer.component';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-register-form-pet-house',
  imports: [FormsModule, ReactiveFormsModule, FooterComponent, CommonModule],
  templateUrl: './register-form-pet-house.component.html',
  styleUrl: './register-form-pet-house.component.css',
})
export class RegisterFormPetHouseComponent {
  registerForm: FormGroup;
  errores: { [key: string]: string } = {
    documentoVerificacion: '',
    fotoDireccion: '',
  };
  documentos = {
    documentoVerificacion: { fileName: '', allowedTypes: ['application/pdf'] },
    fotoDireccion: { fileName: '', allowedTypes: ['image/jpeg', 'image/png'] },
  };
  isSubmitting: boolean = false;

  constructor(private fb: FormBuilder) {
    const savedEmail = localStorage.getItem('userEmail') || '';
    this.registerForm = this.fb.group({
      nombre: ['', Validators.required],
      email: [savedEmail, [Validators.required, Validators.email]],
      //password: ['', [Validators.required, Validators.minLength(6)]],

    this.registerForm = this.fb.group({
      nombre: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]],

      ubicacion: ['', Validators.required],
      telefono: [
        '',
        [Validators.required, Validators.pattern('^[0-9]{8,15}$')],
      ],
      nombreRefugio: ['', Validators.required],
      direccion: ['', Validators.required],
      documentoVerificacion: [null, Validators.required],
      fotoDireccion: [null, Validators.required],
    });
  }

  validarArchivo(event: any, tipo: string) {
    const archivo = event.target.files[0];
    const config = this.documentos[tipo as keyof typeof this.documentos];

    if (archivo) {
      const maxSize = 5 * 1024 * 1024; // 5MB

      if (!config.allowedTypes.includes(archivo.type)) {
        this.errores[tipo] =
          tipo === 'documentoVerificacion'
            ? 'Solo se permiten archivos PDF'
            : 'Solo se permiten imágenes (JPG/PNG)';
      } else if (archivo.size > maxSize) {
        this.errores[tipo] = 'El archivo excede el tamaño máximo de 5MB';
      } else {
        this.errores[tipo] = '';
        config.fileName = archivo.name;
        this.registerForm.patchValue({ [tipo]: archivo });
      }
    }
  }

  submitForm() {
    if (
      this.registerForm.valid &&
      Object.values(this.errores).every((e) => e === '')
    ) {
      this.isSubmitting = true;
      // Simular envío al backend
      setTimeout(() => {
        this.isSubmitting = false;
        alert('Registro de hogar solicitado');
        this.registerForm.reset();
        this.documentos = {
          documentoVerificacion: {
            fileName: '',
            allowedTypes: ['application/pdf'],
          },
          fotoDireccion: {
            fileName: '',
            allowedTypes: ['image/jpeg', 'image/png'],
          },
        };
      }, 2000);
    } else {
      this.registerForm.markAllAsTouched();
    }
  }
}
