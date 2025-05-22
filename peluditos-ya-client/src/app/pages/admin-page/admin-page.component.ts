import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common'; 
import { FormsModule } from '@angular/forms';   
import { AdminShelterService } from '../../services/admin-shelter.service';

@Component({
  selector: 'app-admin-shelters',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './admin-page.component.html',
  styleUrls: ['./admin-page.component.css']
})
export class AdminSheltersComponent implements OnInit {
  searchTerm = '';
  casasHogar: any[] = [];
  pendingShelters: any[] = [];

  constructor(private adminShelterService: AdminShelterService) {}

  ngOnInit(): void {
    this.loadShelters();
    this.loadPendingShelters();
  }

  loadShelters(): void {
    this.adminShelterService.getApprovedShelters().subscribe(
      (data) => {
        this.casasHogar = data;
      },
      (error) => {
        console.error('Error loading approved shelters:', error);
      }
    );
  }

  loadPendingShelters(): void {
    this.adminShelterService.getPendingShelters().subscribe(
      (data) => {
        this.pendingShelters = data;
      },
      (error) => {
        console.error('Error loading pending shelters:', error);
      }
    );
  }

  getCasasFiltradas() {
    return this.casasHogar.filter(casa =>
      this.searchTerm === '' || 
      casa.shelterName.toLowerCase().includes(this.searchTerm.toLowerCase())
    );
  }

  eliminarCasa(id: number): void {
    this.adminShelterService.deleteShelter(id).subscribe(
      () => {
        this.casasHogar = this.casasHogar.filter(casa => casa.id !== id);
      },
      (error) => {
        console.error('Error deleting shelter:', error);
      }
    );
  }

  approveShelter(id: number): void {
    this.adminShelterService.approveShelter(id).subscribe(
      () => {
        this.pendingShelters = this.pendingShelters.filter(s => s.id !== id);
        this.loadShelters();
      },
      (error) => {
        console.error('Error approving shelter:', error);
      }
    );
  }

  rejectShelter(id: number): void {
    this.adminShelterService.rejectShelter(id).subscribe(
      () => {
        this.pendingShelters = this.pendingShelters.filter(s => s.id !== id);
      },
      (error) => {
        console.error('Error rejecting shelter:', error);
      }
    );
  }
}
