import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, BehaviorSubject } from 'rxjs';
import { map } from 'rxjs/operators';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AuthAdopterService {
  private isAdminSubject = new BehaviorSubject<boolean>(false);
  private currentUserSubject = new BehaviorSubject<any>(null);
  
  isAdmin$ = this.isAdminSubject.asObservable();
  currentUser$ = this.currentUserSubject.asObservable();

  private apiUrl = `${environment.apiBaseUrl}/auth`;

  constructor(private http: HttpClient) {
    // Recuperar estado de localStorage al inicializar
    this.loadUserFromStorage();
  }

  register(userData: any): Observable<string> {
    return this.http.post(
      `${this.apiUrl}/signup-adopter`, 
      userData,
      { responseType: 'text' }
    );
  }

  login(credentials: { email: string; password: string }): Observable<any> {
    return this.http.post<{ message: string; role: string; admin: boolean; userId: number; shelterId?: number }>(
      `${this.apiUrl}/login`,
      credentials
    ).pipe(
      map(response => {
        // Guardar información del usuario en localStorage
        localStorage.setItem('userId', response.userId.toString());
        localStorage.setItem('isAdmin', response.admin.toString());
        localStorage.setItem('userRole', response.role || 'ADOPTER');
        
        if (response.shelterId) {
          localStorage.setItem('shelterId', response.shelterId.toString());
        } else {
          localStorage.removeItem('shelterId'); // Limpiar si no es shelter
        }

        // Actualizar subjects
        this.isAdminSubject.next(response.admin);
        this.currentUserSubject.next({
          userId: response.userId,
          isAdmin: response.admin,
          role: response.role,
          shelterId: response.shelterId
        });

        console.log('Login successful:', response);
        return response;
      })
    );
  }

  private loadUserFromStorage(): void {
    const userId = localStorage.getItem('userId');
    const isAdmin = localStorage.getItem('isAdmin') === 'true';
    const userRole = localStorage.getItem('userRole');
    const shelterId = localStorage.getItem('shelterId');

    if (userId) {
      this.isAdminSubject.next(isAdmin);
      this.currentUserSubject.next({
        userId: parseInt(userId),
        isAdmin,
        role: userRole,
        shelterId: shelterId ? parseInt(shelterId) : null
      });
    }
  }

  isLoggedIn(): boolean {
    return localStorage.getItem('userId') !== null;
  }

  getCurrentUser(): any {
    return this.currentUserSubject.value;
  }

  getUserRole(): string | null {
    return localStorage.getItem('userRole');
  }

  getShelterId(): number | null {
    const shelterId = localStorage.getItem('shelterId');
    return shelterId ? parseInt(shelterId) : null;
  }

  isAdminUser(): boolean {
    return localStorage.getItem('isAdmin') === 'true';
  }
  
  logout() {
    this.isAdminSubject.next(false);
    this.currentUserSubject.next(null);
    localStorage.clear();
  }
  
}
