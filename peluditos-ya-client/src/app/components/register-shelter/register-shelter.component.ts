import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { AuthShelterService } from '../../services';
import { Router } from '@angular/router';
import * as L from 'leaflet';


@Component({
  selector: 'app-register-shelter',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './register-shelter.component.html',
  styleUrls: ['./register-shelter.component.css']
})
export class RegisterShelterComponent {
  registerForm: FormGroup;

  constructor(private fb: FormBuilder, private authService: AuthShelterService, private router: Router) {
    this.registerForm = this.fb.group({
      description: ['', [Validators.required, Validators.minLength(10)]],
      ownername: ['', [Validators.required, Validators.minLength(3)]],
      documentNumber: ['', [Validators.required, Validators.pattern('[0-9]{7,15}')]],
      shelterAddress: ['', Validators.required],
      name: ['', Validators.required], // Coincide con el HTML
      phone: ['', [Validators.required, Validators.pattern('[0-9]{7,15}')]],
      city: ['', Validators.required], 
      latitude: ['', Validators.required],
      longitude: ['', Validators.required],
      isAdmin: ['', false],
      acceptTerms: [false, Validators.requiredTrue]
    });
    
  }

  onSubmit() {
    if (this.registerForm.valid) {
      const userId = localStorage.getItem('userId');
      if (!userId) {
        console.error('No se encontró el userId en el Local Storage');
        alert('Error: No se encontró el ID del usuario. Por favor, inicia sesión nuevamente.');
        return;
      }
  
      console.log('User ID obtenido del Local Storage:', userId);
  
      const payload = {
        userId: parseInt(userId, 10),
        description: this.registerForm.value.description,
        shelterName: this.registerForm.value.name,
        shelterAddress: this.registerForm.value.shelterAddress,
        phone: this.registerForm.value.phone,
        city: this.registerForm.value.city,
        documentNumber: this.registerForm.value.documentNumber,
        latitude: this.registerForm.value.latitude,
        longitude: this.registerForm.value.longitude
      };

  
      console.log('Enviando datos:', payload);
  
      this.authService.submitShelterRequest(payload).subscribe({
        next: (response) => {
          console.log('Solicitud enviada:', response);
          alert('Tu solicitud fue enviada con éxito');
          this.registerForm.reset();
        },
        error: (error) => {
          console.error('Error al enviar solicitud:', error);
          alert('Error al enviar la solicitud');
        }
      });
    } else {
      Object.values(this.registerForm.controls).forEach(control => {
        control.markAsTouched();
      });
    }
  }
  

  openTerms() {
    console.log('Terms and conditions clicked');
  }

  navigateToLogin() {
    this.router.navigate(['/login']);
  }

map!: L.Map;
marker!: L.Marker;

ngAfterViewInit() {
  this.initMap();
}

private initMap(): void {
  this.map = L.map('map').setView([-16.5, -68.15], 13);

  L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
    maxZoom: 19,
  }).addTo(this.map);

  this.map.on('dblclick', (e: L.LeafletMouseEvent) => {
  const { lat, lng } = e.latlng;

  if (!this.marker) {
    this.marker = L.marker([lat, lng], {
      draggable: true
    }).addTo(this.map);
  } else {
    this.marker.setLatLng([lat, lng]);
  }

  this.registerForm.patchValue({
    latitude: lat,
    longitude: lng
  });

  console.log('Lat:', lat, 'Lng:', lng);
});
  }
}