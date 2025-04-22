import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { FooterComponent } from '../../components/footer/footer.component';
import { RouterModule } from '@angular/router';
import { AnimalService } from '../../services/auth.animal.service'; // Importamos el servicio

// Interfaz para la mascota que esperamos en el frontend
interface Mascota {
  nombre: string;
  edad: string;
  sexo: string;
  tipo: string;
  descripcion: string;
  imagen: string;
}

@Component({
  selector: 'app-adoption',
  standalone: true,
  imports: [CommonModule, FormsModule, FooterComponent, RouterModule],
  templateUrl: './adoption.component.html',
  styleUrls: ['./adoption.component.css']
})
export class AdoptionComponent implements OnInit {
  searchTerm = '';
  filtroEdad = '';
  filtroSexo = '';
  filtroTipo = '';

  // Declarar las mascotas como un arreglo de tipo 'Mascota'
  mascotas: Mascota[] = [];
  usuarioAutenticado: boolean = false; // Variable para controlar si el usuario está autenticado


  constructor(private animalService: AnimalService) {}

  ngOnInit(): void {
    this.obtenerMascotas();
    this.verificarUsuario();  // Verificamos si el usuario está autenticado
  }

  obtenerMascotas() {
    this.animalService.obtenerMascotas().subscribe(
      (response) => {
        this.mascotas = response.map((mascota: any) => {
          const edad = mascota.age;

          if (edad < 3) {
            this.filtroEdad = 'cachorro'; 
          }

          return {
            nombre: mascota.name,
            tipo: mascota.animalType,
            edad: edad.toString(),
            descripcion: `Tiene ${mascota.age} años de edad,  ${this.traducirTipo(mascota.animalType).toLowerCase()} de raza ${mascota.breed}`,
            imagen: mascota.photoPath
          };
        });
      },
      (error) => {
        console.error('Error al obtener mascotas:', error);
      }
    );
  }


  // Filtrar las mascotas según los criterios de búsqueda
  getMascotasFiltradas() {
    return this.mascotas.filter(mascota =>
      (this.searchTerm === '' || mascota.nombre.toLowerCase().includes(this.searchTerm.toLowerCase())) &&
      (this.filtroEdad === '' || mascota.edad === this.filtroEdad) &&
      (this.filtroSexo === '' || mascota.sexo === this.filtroSexo) &&
      (this.filtroTipo === '' || mascota.tipo === this.filtroTipo)
    );
  }

  traducirTipo(tipo: string): string {
    switch(tipo.toLowerCase()) {
      case 'dog':
        return 'Perro';
      case 'cat':
        return 'Gato';
      default:
        return tipo; // Si no es ni perro ni gato, retorna el tipo tal cual
    }
  }

  // Verifica si el usuario está autenticado
  verificarUsuario() {
    const email = localStorage.getItem('userEmail');
    this.usuarioAutenticado = email !== null && email !== '';
  }
}
