import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { AuthAdopterService } from '../services/auth.adopter.service';

@Injectable({
  providedIn: 'root'
})
export class ShelterGuard implements CanActivate {

  constructor(
    private authService: AuthAdopterService,
    private router: Router
  ) {}

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): Observable<boolean> | Promise<boolean> | boolean {
    
    // Verificar si el usuario está logueado
    if (!this.authService.isLoggedIn()) {
      this.router.navigate(['/login']);
      return false;
    }

    // Verificar si es administrador o encargado de refugio
    const userRole = localStorage.getItem('userRole');
    const isAdmin = localStorage.getItem('isAdmin') === 'true';
    const shelterId = localStorage.getItem('shelterId');

    if (isAdmin || (userRole === 'shelter' && shelterId)) {
      return true;
    }

    // Si no es admin ni encargado de refugio, denegar acceso
    this.router.navigate(['/access-denied']);
    return false;
  }
}
