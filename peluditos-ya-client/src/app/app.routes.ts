import { Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { RegisterComponent } from './components/register/register.component';
import { LoginComponent } from './components/login/login.component';
import { EnAdopcionComponent } from './pages/en-adopcion/en-adopcion.component';
import { CasasHogarComponent } from './pages/casas-hogar/casas-hogar.component';
import { RegisterFormPetHouseComponent } from './pages/register-form-pet-house/register-form-pet-house.component';

export const routes: Routes = [
    {
        path: '',
        component: HomeComponent,
        title: 'Home'
    },
    {
        path: 'register',
        component: RegisterComponent,
        title: 'Register'
    },
    {   
        path: 'login', 
        component: LoginComponent,
        title: 'Login'
    },
    {
        path: 'en-adopcion',
        component: EnAdopcionComponent,
        title: 'En Adopcion'
    },
    {
        path: 'casas-hogar',
        component: CasasHogarComponent,
        title: 'Casas Hogar'
    },
    {
        path: 'register-form-pet-house',
        component: RegisterFormPetHouseComponent,
        title: 'Register Form Pet House'
    },
    { 
        path: '**', 
        redirectTo: '' 
    }
];
