import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink, RouterLinkActive } from '@angular/router';
import { AuthAdopterService } from '../../services';

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
    { label: 'Sobre Nosotros', route: '/about-us' },
    { label: 'En Adopción', route: '/adoption' },
    { label: 'Casas Hogar', route: '/shelter' },
    { label: 'Contacto', route: '/contacto' }
  ];

  isAdmin: boolean = false;
  isLoggedIn: boolean = false; 

  constructor(public authService: AuthAdopterService) {}

  ngOnInit() {
    this.checkAdminStatus();
    this.checkLoginStatus();

    // Escuchar cambios en localStorage (si se desloguea desde otra pestaña)
    window.addEventListener('storage', () => {
      this.checkAdminStatus();
      this.checkLoginStatus();
    });
  }

  checkAdminStatus() {
    this.isAdmin = localStorage.getItem('isAdmin') === 'true';
  }

  checkLoginStatus() {
    this.isLoggedIn = !!localStorage.getItem('userId');
  }

  logout() {
    this.authService.logout();
    window.location.reload(); // Opcional: actualizar la vista
  }
}

