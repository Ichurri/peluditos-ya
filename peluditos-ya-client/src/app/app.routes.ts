import { Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { RegisterComponent } from './components/register/register.component';
import { LoginComponent } from './components/login/login.component';
import { EnAdopcionComponent } from './pages/en-adopcion/en-adopcion.component';

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
        path: '**', 
        redirectTo: '' 
    }
];
