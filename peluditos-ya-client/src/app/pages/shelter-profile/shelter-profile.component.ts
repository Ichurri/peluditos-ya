import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';
import { RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { AuthShelterService } from '../../services/auth.shelter.service';
import * as L from 'leaflet';


@Component({
  selector: 'app-shelter-profile',
  templateUrl: './shelter-profile.component.html',
  styleUrls: ['./shelter-profile.component.css'],
  standalone: true,
  imports: [CommonModule, RouterModule, FormsModule]
})

export class ShelterProfileComponent implements OnInit {
  shelter: any;
  canEdit = true; // Cambiar esto según autenticación/rol del usuario actual
  selectedRating: number = 3;


  constructor(
    private route: ActivatedRoute,
    private AuthShelterService: AuthShelterService,
    private router: Router
  ) {}

mapInitialized = false;

ngOnInit(): void {
  const shelterId = this.route.snapshot.paramMap.get('id');
  if (shelterId) {
    this.AuthShelterService.getShelterById(+shelterId).subscribe({
      next: (data) => {
        this.shelter = data;

        // Esperamos a que Angular renderice el div con *ngIf
        setTimeout(() => {
          this.tryInitMap();
        }, 100);
      },
      error: (err) => {
        console.error('Error al obtener el refugio:', err);
      }
    });
  }
}

tryInitMap(): void {
  const mapContainer = document.getElementById('map');

  if (!mapContainer) {
    console.warn('Map container still not found, retrying...');
    setTimeout(() => this.tryInitMap(), 100); // Reintenta hasta que el DOM lo renderice
    return;
  }

  if (!this.mapInitialized && this.shelter?.latitude && this.shelter?.longitude) {
    this.initMap();
    this.mapInitialized = true;
  }
}

  getStatusClass(status: string): string {
    switch (status.toLowerCase()) {
      case 'approved': return 'approved';
      case 'pending': return 'pending';
      case 'rejected': return 'rejected';
      default: return '';
    }
  }

  viewAnimals() {
    this.router.navigate(['/animals'], {
      queryParams: { shelterId: this.shelter.id }
    });
  }

  editShelter() {
    this.router.navigate(['/shelters', this.shelter.id, 'edit']);
  }

  onRatingChange(): void {
    console.log('Nueva calificación seleccionada:', this.selectedRating);
  }

  initMap(): void {
  if (!this.shelter?.latitude || !this.shelter?.longitude) {
    console.error('No se puede inicializar el mapa sin coordenadas');
    return;
  }

  const mapContainer = document.getElementById('map');
  if (!mapContainer) {
    console.error('Map container not found');
    return;
  }

  const map = L.map('map').setView([this.shelter.latitude, this.shelter.longitude], 15);

  L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
    attribution: '© OpenStreetMap contributors'
  }).addTo(map);

  L.marker([this.shelter.latitude, this.shelter.longitude])
    .addTo(map)
    .bindPopup(this.shelter.shelterAddress)
    .openPopup();
}
}
