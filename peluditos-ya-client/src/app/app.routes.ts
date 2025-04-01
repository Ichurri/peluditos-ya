import { Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { RegisterComponent } from './components/register-form-user/register.component';
import { LoginComponent } from './components/login/login.component';
import { AdoptionComponent } from './pages/adoption/adoption.component';
import { ShelterComponent } from './pages/shelter/shelter.component';

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
        path: '**', 
        redirectTo: '' 
    }
];
