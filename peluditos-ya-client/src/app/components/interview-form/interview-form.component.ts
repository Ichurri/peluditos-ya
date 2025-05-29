import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { format } from 'date-fns';
import { Router } from '@angular/router';
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { ReactiveFormsModule } from '@angular/forms';
import { environment } from '../../../environments/environment';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-interview-form',
  templateUrl: './interview-form.component.html',
  styleUrls: ['./interview-form.component.css'], 
  imports: [ReactiveFormsModule, CommonModule]
})
export class InterviewFormComponent implements OnInit {
  interviewForm!: FormGroup;
  petName!: string;

  constructor(
    private fb: FormBuilder,
    private http: HttpClient,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.petName = localStorage.getItem('selectedPetName') || 'Mascota';
    this.interviewForm = this.fb.group({
      userName: ['', Validators.required],
      userEmail: ['', [Validators.required, Validators.email]],
      shelterEmail: ['', [Validators.required, Validators.email]],
      interviewDateTime: ['', Validators.required],
      description: ['']
    });
  }

  submitForm(): void {
    const url = `${environment.apiBaseUrl}/interviews`;
    if (this.interviewForm.invalid) return;
    const formData = {
      ...this.interviewForm.value,
      petName: this.petName
    };

    this.http.post(url, formData).subscribe({
      next: () => {
        alert('Solicitud enviada correctamente'),
        this.router.navigate(['/adoption']);
      },
      error: (err) => {
        console.error('Error detallado al enviar la solicitud', err);
        alert('Ocurrió un error al enviar la solicitud.');
    }
  });
  }
}
