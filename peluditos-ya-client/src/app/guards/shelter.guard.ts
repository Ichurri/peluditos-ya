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

    // Para rutas que requieren específicamente ser SHELTER (no admin)
    const requiresShelter = route.data?.['requiresShelter'] || false;
    
    if (requiresShelter) {
      const userRole = localStorage.getItem('userRole');
      const shelterId = localStorage.getItem('shelterId');

      if (userRole === 'SHELTER' && shelterId) {
        return true;
      } else {
        this.router.navigate(['/access-denied']);
        return false;
      }
    }

    // Por defecto, permitir tanto admin como shelter
    const userRole = localStorage.getItem('userRole');
    const isAdmin = localStorage.getItem('isAdmin') === 'true';
    const shelterId = localStorage.getItem('shelterId');

    if (isAdmin || (userRole === 'SHELTER' && shelterId)) {
      return true;
    }

    this.router.navigate(['/access-denied']);
    return false;
  }
}
