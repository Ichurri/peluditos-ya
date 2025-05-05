import { Component, OnInit } from '@angular/core';
import { AuthShelterService } from '../../services';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { FooterComponent } from '../../components/footer/footer.component';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-shelter',
  standalone: true,
  imports: [CommonModule, FormsModule, FooterComponent, RouterModule],
  templateUrl: './shelter.component.html',
  styleUrls: ['./shelter.component.css']
})
export class ShelterComponent implements OnInit {
  searchTerm = '';
  casasHogar: any[] = [];

  constructor(private AuthShelterService: AuthShelterService) {}

  ngOnInit(): void {
    this.loadShelters();
  }

  loadShelters(): void {
    this.AuthShelterService.getApprovedShelters().subscribe({
      next: (data) => {
        this.casasHogar = data.map(shelter => ({
          nombre: shelter.shelterName,
          descripcion: shelter.shelterAddress,
          imagen: 'https://img.freepik.com/vector-premium/cuidado-animales-hogar-emblema-refugio-animales-adopcion-o-pancarta-diseno-logotipo_125133-2655.jpg' 
        }));
      },
      error: (error) => {
        console.error('Error cargando refugios:', error);
        this.casasHogar = [
          { 
            nombre: 'Hogar San Francisco', 
            descripcion: 'Ubicado en el centro de la ciudad.', 
            imagen: 'https://img.freepik.com/vector-premium/cuidado-animales-hogar-emblema-refugio-animales-adopcion-o-pancarta-diseno-logotipo_125133-2655.jpg' 
          },
          { 
            nombre: 'Refugio Esperanza', 
            descripcion: 'Un hogar con espacios amplios.', 
            imagen: 'https://img.freepik.com/vector-premium/cuidado-animales-hogar-emblema-refugio-animales-adopcion-o-pancarta-diseno-logotipo_125133-2655.jpg' 
          }
        ];
      }
    });
  }

  getCasasFiltradas() {
    return this.casasHogar.filter(casa =>
      this.searchTerm === '' || casa.nombre.toLowerCase().includes(this.searchTerm.toLowerCase())
    );
  }
}