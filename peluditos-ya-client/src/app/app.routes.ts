import { Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { RegisterAdopterComponent } from './components/register-adopter/register-adopter.component';
import { LoginAdopterComponent } from './components/login-adopter/login-adopter.component';
import { AdoptionComponent } from './pages/adoption/adoption.component';
import { ShelterComponent } from './pages/shelter/shelter.component';
import { RegisterShelterComponent } from './components/register-shelter/register-shelter.component';

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
        path: 'login-adopter', 
        component: LoginAdopterComponent,
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
        path: '**', 
        redirectTo: '' 
    }
];
