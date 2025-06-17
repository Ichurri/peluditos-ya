import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';

@Component({
  selector: 'app-access-denied',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div class="access-denied-container">
      <div class="access-denied-content">
        <div class="icon">
          <i class="fas fa-lock"></i>
        </div>
        <h1>Acceso Denegado</h1>
        <p>No tienes permisos para acceder a esta sección.</p>
        <div class="info">
          <h3>¿Qué puedes hacer?</h3>
          <ul>
            <li>Si eres administrador, asegúrate de haber iniciado sesión correctamente</li>
            <li>Si eres encargado de refugio, solo puedes acceder a la gestión de tus propias mascotas</li>
            <li>Si necesitas acceso, contacta con el administrador del sistema</li>
          </ul>
        </div>
        <div class="actions">
          <button (click)="goHome()" class="btn btn-primary">
            <i class="fas fa-home"></i> Ir al Inicio
          </button>
          <button (click)="goLogin()" class="btn btn-secondary">
            <i class="fas fa-sign-in-alt"></i> Iniciar Sesión
          </button>
        </div>
      </div>
    </div>
  `,
  styles: [`
    .access-denied-container {
      display: flex;
      justify-content: center;
      align-items: center;
      min-height: 80vh;
      padding: 2rem;
      background: linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%);
    }

    .access-denied-content {
      text-align: center;
      background: white;
      padding: 3rem;
      border-radius: 12px;
      box-shadow: 0 8px 30px rgba(0, 0, 0, 0.12);
      max-width: 500px;
      width: 100%;
    }

    .icon {
      font-size: 4rem;
      color: #e74c3c;
      margin-bottom: 1rem;
    }

    h1 {
      color: #2c3e50;
      font-size: 2rem;
      margin-bottom: 1rem;
    }

    p {
      color: #7f8c8d;
      font-size: 1.1rem;
      margin-bottom: 2rem;
    }

    .info {
      text-align: left;
      background: #f8f9fa;
      padding: 1.5rem;
      border-radius: 8px;
      margin-bottom: 2rem;
    }

    .info h3 {
      color: #2c3e50;
      margin-bottom: 1rem;
    }

    .info ul {
      color: #555;
      line-height: 1.6;
    }

    .info li {
      margin-bottom: 0.5rem;
    }

    .actions {
      display: flex;
      gap: 1rem;
      justify-content: center;
      flex-wrap: wrap;
    }

    .btn {
      padding: 0.75rem 1.5rem;
      border: none;
      border-radius: 6px;
      font-size: 1rem;
      cursor: pointer;
      transition: all 0.3s ease;
      display: inline-flex;
      align-items: center;
      gap: 0.5rem;
      text-decoration: none;
    }

    .btn-primary {
      background: #3498db;
      color: white;
    }

    .btn-primary:hover {
      background: #2980b9;
    }

    .btn-secondary {
      background: #95a5a6;
      color: white;
    }

    .btn-secondary:hover {
      background: #7f8c8d;
    }

    @media (max-width: 768px) {
      .access-denied-content {
        padding: 2rem;
      }

      .actions {
        flex-direction: column;
      }

      .btn {
        width: 100%;
        justify-content: center;
      }
    }
  `]
})
export class AccessDeniedComponent {
  
  constructor(private router: Router) {}

  goHome(): void {
    this.router.navigate(['/home']);
  }

  goLogin(): void {
    this.router.navigate(['/login']);
  }
}
