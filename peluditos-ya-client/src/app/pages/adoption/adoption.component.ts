import { Component, OnInit, AfterViewInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { FooterComponent } from '../../components/footer/footer.component';
import { RouterModule } from '@angular/router';
import { AnimalService } from '../../services/auth.animal.service';

// Interfaz para las mascotas
interface Mascota {
  id: any | string;
  nombre: string;
  edad: string; // Ahora puede ser 'cachorro' o 'adulto'
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
export class AdoptionComponent implements OnInit, AfterViewInit {

  searchTerm = '';
  filtroEdad = '';
  filtroSexo = '';
  filtroTipo = '';

  mascotas: Mascota[] = [];
  usuarioAutenticado: boolean = false;

  // Array de URLs para las imágenes del carrusel
  carouselImageUrls: string[] = [
    'https://patitasalrescateorg.netlify.app/img/carousel1.jpg',
    'https://patitasalrescateorg.netlify.app/img/carousel2.jpg',
    'https://patitasalrescateorg.netlify.app/img/carousel3.jpg',
    'https://patitasalrescateorg.netlify.app/img/carousel4.jpg',
    'https://patitasalrescateorg.netlify.app/img/carousel5.jpg',
    'https://patitasalrescateorg.netlify.app/img/carousel6.jpg',
    'https://patitasalrescateorg.netlify.app/img/carousel7.jpg',
    'https://patitasalrescateorg.netlify.app/img/carousel8.jpg',
    'https://patitasalrescateorg.netlify.app/img/carousel9.jpg',
    'https://patitasalrescateorg.netlify.app/img/carousel10.jpg',
    'https://patitasalrescateorg.netlify.app/img/carousel11.jpg',
    'https://patitasalrescateorg.netlify.app/img/carousel12.jpg',
  ];

  constructor(private animalService: AnimalService) {}

  ngOnInit(): void {
    this.obtenerMascotas();
    this.verificarUsuario();
    this.carouselImageUrls = [...this.carouselImageUrls, ...this.carouselImageUrls];
  }

  ngAfterViewInit(): void {
    // Iniciar el auto-scroll del carrusel una vez que la vista se ha inicializado.
    this.iniciarCarrusel();
  }

  adoptarMascota(nombre:string){
    localStorage.setItem('selectedPetName', nombre)
  }

  obtenerMascotas() {
    this.animalService.obtenerMascotas().subscribe(
      (response) => {
        this.mascotas = response.map((mascota: any) => {
          const edadNumerica = mascota.age;
          let edadTexto: string;

          // Determinar si es 'cachorro' o 'adulto' para el filtro
          if (edadNumerica < 3) { // Puedes ajustar este umbral según tu definición de cachorro
            edadTexto = 'cachorro';
          } else {
            edadTexto = 'adulto';
          }

          return {
            id: mascota.id,
            nombre: mascota.name,
            tipo: mascota.animalType,
            edad: edadTexto, // Usamos 'cachorro' o 'adulto'
            sexo: mascota.sex,
            descripcion: `Tiene ${mascota.age} años de edad, ${this.traducirTipo(mascota.animalType).toLowerCase()} de raza ${mascota.breed}`,
            imagen: mascota.photoUrl || 'https://files.lafm.com.co/assets/public/styles/twitter/public/2023-08/murio_cheems_el_perrito_de_los_meme.jpg.webp?VersionId=dHwATkyc2gQxvSwNeWXyOFUXPzaF3bbQ&itok=MmkIcD6M' // Imagen por defecto si no hay URL
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
      (this.filtroEdad === '' || mascota.edad === this.filtroEdad) && // Compara con 'cachorro' o 'adulto'
      (this.filtroSexo === '' || mascota.sexo === this.filtroSexo) &&
      (this.filtroTipo === '' || mascota.tipo === this.filtroTipo)
    );
  }

  traducirTipo(tipo: string): string {
    switch (tipo.toLowerCase()) {
      case 'dog':
        return 'Perro';
      case 'cat':
        return 'Gato';
      default:
        return tipo;
    }
  }

  verificarUsuario() {
    const email = localStorage.getItem('userEmail');
    this.usuarioAutenticado = email !== null && email !== '';
  }



  // Función para iniciar el auto-scroll del carrusel
  iniciarCarrusel() {
    const carousel = document.querySelector('.tricky-cards-container') as HTMLElement;
    if (!carousel) {
      console.warn('Contenedor del carrusel (.tricky-cards-container) no encontrado para el auto-scroll.');
      return;
    }

    let scrollAmount = 0;
    // Ajustar scrollStep y delay para un movimiento muy suave y lento
    const scrollStep = 0.5; // Menos pasos por intervalo para más suavidad
    const delay = 30;  // Mayor delay entre pasos para más lentitud

    let isScrollingPaused = false; // Bandera para controlar la pausa del scroll

    // Pausar el scroll al pasar el mouse por encima del carrusel
    carousel.addEventListener('mouseenter', () => {
      isScrollingPaused = true;
      carousel.style.cursor = 'grab'; // Cambiar cursor para indicar que se puede arrastrar
    });

    // Reanudar el scroll al quitar el mouse del carrusel
    carousel.addEventListener('mouseleave', () => {
      isScrollingPaused = false;
      carousel.style.cursor = 'auto'; // Restaurar el cursor por defecto
    });

    const autoScroll = () => {
      if (isScrollingPaused) {
        requestAnimationFrame(autoScroll); // Si está pausado, seguir pidiendo frames pero sin mover
        return;
      }

      const halfWidth = carousel.scrollWidth / 2;

      if (carousel.scrollLeft >= halfWidth) {
        carousel.scrollLeft -= halfWidth;
        scrollAmount = carousel.scrollLeft; // Sincronizar scrollAmount con la nueva posición
      } else {
        scrollAmount += scrollStep;
      }

      carousel.scrollTo({
        left: scrollAmount,
        behavior: 'smooth' // Mantiene el desplazamiento suave
      });

      requestAnimationFrame(autoScroll);
    };

    requestAnimationFrame(autoScroll);
  }
}