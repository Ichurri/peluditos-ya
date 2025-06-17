import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { AuthAdopterService } from '../services/auth.adopter.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {

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

    // Para rutas que requieren admin (como pets-management)
    const requiresAdmin = route.data?.['requiresAdmin'] || false;
    
    if (requiresAdmin) {
      return this.authService.isAdmin$.pipe(
        map(isAdmin => {
          if (!isAdmin) {
            // Redirigir a página de acceso denegado
            this.router.navigate(['/access-denied']);
            return false;
          }
          return true;
        })
      );
    }

    return true;
  }
}
