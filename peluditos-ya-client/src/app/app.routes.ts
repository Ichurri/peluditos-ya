import { Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { RegisterAdopterComponent } from './components/register-adopter/register-adopter.component';
import { AdoptionComponent } from './pages/adoption/adoption.component';
import { ShelterComponent } from './pages/shelter/shelter.component';
import { RegisterShelterComponent } from './components/register-shelter/register-shelter.component';
import { AdminSheltersComponent } from './pages/admin-page/admin-page.component';
import { LoginComponent } from './components/login/login.component';
import { RegisterAnimalComponent } from './components/register-animal/register-animal.component';

export const routes: Routes = [
    {
        path: '',
        component: HomeComponent,
        title: 'Home'
    },
    {
        path: 'register-adopter',
        component: RegisterAdopterComponent,
        title: 'Register Adopter'
    },
    {   
        path: 'login', 
        component: LoginComponent,
        title: 'Login Adopter'
    },
    {
        path: 'adoption',
        component: AdoptionComponent,
        title: 'Adoption'
    },
    {
        path: 'shelter',
        component: ShelterComponent,
        title: 'Shelter'
    },
    {
        path: 'register-shelter',
        component: RegisterShelterComponent,
        title: 'Register Shelter'       
    },
    {
        path: 'admin-dashboard',
        component: AdminSheltersComponent,
        title: 'View Admin'       
    },
    {
        path: 'register-animal',
        component: RegisterAnimalComponent,
        title: 'Register Animal'       
    },
    { 
        path: '**', 
        redirectTo: '' 
    }
];
