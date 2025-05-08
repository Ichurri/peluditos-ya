import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';
import { RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { AuthShelterService } from '../../services/auth.shelter.service';

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

  ngOnInit(): void {
    const shelterId = this.route.snapshot.paramMap.get('id');
    console.log('Shelter ID from URL:', shelterId); // Depuración
    if (shelterId) {
      this.AuthShelterService.getShelterById(+shelterId).subscribe({
        next: (data) => {
          console.log('Shelter data:', data); // Depuración
          this.shelter = data;
        },
        error: (err) => console.error('Error cargando refugio', err)
      });
    } else {
      console.error('No se encontró un ID válido en la URL');
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
}
