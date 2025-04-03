import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common'; // <-- Añade esto
import { FormsModule } from '@angular/forms';   // <-- Añade esto
import { AuthShelterService } from '../../services';

@Component({
  selector: 'app-admin-shelters',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './admin-page.component.html',
  styleUrls: ['./admin-page.component.css']
})
export class AdminSheltersComponent implements OnInit {
  searchTerm = '';
  casasHogar: any[] = [];

  constructor(private AuthShelterService: AuthShelterService) {}

  ngOnInit(): void {
    this.loadShelters();
  }

  loadShelters(): void {
    this.AuthShelterService.getShelters().subscribe(
      (data) => {
        this.casasHogar = data;
      },
      (error) => {
        console.error('Error al cargar refugios:', error);
      }
    );
  }

  getCasasFiltradas() {
    return this.casasHogar.filter(casa =>
      this.searchTerm === '' || 
      casa.shelterName.toLowerCase().includes(this.searchTerm.toLowerCase())
    );
  }

  eliminarCasa(id: number): void {
    this.AuthShelterService.deleteShelter(id).subscribe(
      () => {
        this.casasHogar = this.casasHogar.filter(casa => casa.id !== id);
      },
      (error) => {
        console.error('Error al eliminar refugio:', error);
      }
    );
  }
}