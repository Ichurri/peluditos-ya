import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { FooterComponent } from '../../components/footer/footer.component';

@Component({
  selector: 'app-en-adopcion',
  standalone: true,
  imports: [CommonModule, FormsModule, FooterComponent],
  templateUrl: './en-adopcion.component.html',
  styleUrl: './en-adopcion.component.css'
})
export class EnAdopcionComponent {
  searchTerm = '';
  filtroEdad = '';
  filtroSexo = '';
  filtroTipo = '';

  mascotas = [
    { nombre: 'Luna', edad: 'cachorro', sexo: 'hembra', tipo: 'perro', descripcion: 'Juguetona y cariñosa.', imagen: 'https://estudiantes.ucontinental.edu.pe/wp-content/uploads/2020/03/Datos-que-debes-tener-en-cuenta-si-tienes-una-mascota-en-casa.jpg' },
    { nombre: 'Simba', edad: 'adulto', sexo: 'macho', tipo: 'gato', descripcion: 'Tranquilo y amoroso.', imagen: 'https://www.purina.es/sites/default/files/2021-12/Getting-A-Cat1080x608.jpg' },
    { nombre: 'Rocky', edad: 'cachorro', sexo: 'macho', tipo: 'perro', descripcion: 'Lleno de energía.', imagen: 'https://cdn.pixabay.com/photo/2017/09/25/13/12/dog-2785074_1280.jpg' },
    { nombre: 'Mia', edad: 'adulto', sexo: 'hembra', tipo: 'gato', descripcion: 'Independiente y curiosa.', imagen: 'https://www.astrolabio.com.mx/wp-content/uploads/2016/02/como-cuidar-a-tu-gato-cuidados-nombres-mascotas-foto.jpg'}
  ];

  getMascotasFiltradas() {
    return this.mascotas.filter(mascota => 
      (this.searchTerm === '' || mascota.nombre.toLowerCase().includes(this.searchTerm.toLowerCase())) &&
      (this.filtroEdad === '' || mascota.edad === this.filtroEdad) &&
      (this.filtroSexo === '' || mascota.sexo === this.filtroSexo) &&
      (this.filtroTipo === '' || mascota.tipo === this.filtroTipo)
    );
  }
}
