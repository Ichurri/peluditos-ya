import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { AnimalService } from '../../services/auth.animal.service'; 
import { CommonModule } from '@angular/common';


@Component({
  selector: 'app-pet-profile',
  templateUrl: './pet-profile.component.html',
  styleUrls: ['./pet-profile.component.css'],
  imports: [CommonModule]
})
export class PetProfileComponent implements OnInit {
  petId!: number;
  pet: any;
  loading = true;
  error = false;

  constructor(
    private route: ActivatedRoute,
    private animalService: AnimalService
  ) {}

  ngOnInit(): void {
    this.petId = Number(this.route.snapshot.paramMap.get('id'));
    this.getPetProfile();
  }

  getPetProfile(): void {
    this.animalService.getAnimalProfile(this.petId).subscribe({
      next: (data) => {
        this.pet = data;
        this.loading = false;
      },
      error: (err) => {
        console.error('Error al cargar el perfil del animal', err);
        this.error = true;
        this.loading = false;
      }
    });
  }
}