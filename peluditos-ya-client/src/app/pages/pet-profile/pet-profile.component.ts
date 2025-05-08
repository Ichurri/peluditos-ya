import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { AnimalService } from '../../services/auth.animal.service';

// pet-profile.component.ts
@Component({
  selector: 'app-pet-profile',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './pet-profile.component.html',
  styleUrls: ['./pet-profile.component.css']
})
export class PetProfileComponent implements OnInit {
  animal: any;
  loading = true;
  error = false;

  constructor(
    private route: ActivatedRoute,
    private animalService: AnimalService,
    private router: Router
  ) {}

  ngOnInit(): void {
    const animalId = this.route.snapshot.paramMap.get('id');
    if (animalId) {
      this.animalService.getAnimalProfile(+animalId).subscribe({
        next: (data) => {
          this.animal = data;
          this.loading = false;
        },
        error: (err) => {
          console.error('Error loading animal data', err);
          this.error = true;
          this.loading = false;
        }
      });
    }
  }

  navigateToAdoption(): void{
    this.router.navigate(['/adoption']);
  }
}
