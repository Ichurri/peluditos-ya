  import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink, RouterLinkActive } from '@angular/router';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [CommonModule, RouterLink, RouterLinkActive],
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent {
  navigationItems = [
    { label: 'Inicio', route: '/inicio' },
    { label: 'Sobre Nosotros', route: '/sobre-nosotros' },
    { label: 'En Adopción', route: '/adopcion' },
    { label: 'Casas Hogar', route: '/casas-hogar' },
    { label: 'Contacto', route: '/contacto' }
  ];
}