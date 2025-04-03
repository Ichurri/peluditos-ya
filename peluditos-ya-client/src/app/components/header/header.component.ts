import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink, RouterLinkActive } from '@angular/router';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [CommonModule, RouterLink, RouterLinkActive],
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
  navigationItems = [
    { label: 'Inicio', route: '/home' },
    { label: 'Sobre Nosotros', route: '/sobre-nosotros' },
    { label: 'En Adopción', route: '/adoption' },
    { label: 'Casas Hogar', route: '/shelter' },
    { label: 'Contacto', route: '/contacto' }
  ];

  isAdmin: boolean = false;

  ngOnInit() {
    // Recuperamos el valor de isAdmin desde localStorage
    this.isAdmin = localStorage.getItem('isAdmin') === 'true';
  }
}
