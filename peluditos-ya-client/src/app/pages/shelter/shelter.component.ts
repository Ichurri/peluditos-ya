import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { FooterComponent } from '../../components/footer/footer.component';
import { RouterLink, RouterModule } from '@angular/router';

@Component({
  selector: 'app-shelter',
  templateUrl: './shelter.component.html',
  imports: [CommonModule, FormsModule, FooterComponent, RouterModule],
  styleUrls: ['./shelter.component.css']
})
export class ShelterComponent {
  searchTerm = '';

  casasHogar = [
    { nombre: 'Hogar San Francisco', descripcion: 'Ubicado en el centro de la ciudad.', imagen: 'https://img.freepik.com/vector-premium/cuidado-animales-hogar-emblema-refugio-animales-adopcion-o-pancarta-diseno-logotipo_125133-2655.jpg' },
    { nombre: 'Refugio Esperanza', descripcion: 'Un hogar con espacios amplios.', imagen: 'https://petepua.com/wp-content/uploads/2022/01/Adopcion-de-perros-y-gatos-en-Bolivia.-Fundacion-Casa-de-los-Angeles-logo.-heroes-sin-capa..jpg.webp' },
    { nombre: 'Casa de Rescate', descripcion: 'Dedicado al rescate y cuidado de animales.', imagen: 'https://pbs.twimg.com/profile_images/1039525888730308608/-8viHVUh_400x400.jpg' },
    { nombre: 'Refugio Vida Animal', descripcion: 'Brindando amor a los más necesitados.', imagen: 'https://static.vecteezy.com/system/resources/previews/015/413/408/non_2x/adopt-a-pet-cute-homeless-puppy-kitten-hamster-parrot-inside-a-cardboard-box-are-waiting-for-the-adoption-illustration-for-animal-shelter-poster-vector.jpg' }
  ];

  getCasasFiltradas() {
    return this.casasHogar.filter(casa =>
      this.searchTerm === '' || casa.nombre.toLowerCase().includes(this.searchTerm.toLowerCase())
    );
  }
}
