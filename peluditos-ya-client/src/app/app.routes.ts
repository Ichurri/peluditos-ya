import { Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { RegisterAdopterComponent } from './components/register-adopter/register-adopter.component';
import { AdoptionComponent } from './pages/adoption/adoption.component';
import { ShelterComponent } from './pages/shelter/shelter.component';
import { RegisterShelterComponent } from './components/register-shelter/register-shelter.component';
import { AdminSheltersComponent } from './pages/admin-page/admin-page.component';
import { LoginComponent } from './components/login/login.component';
import { RegisterAnimalComponent } from './components/register-animal/register-animal.component';
import { ShelterProfileComponent } from './pages/shelter-profile/shelter-profile.component';
import { PetProfileComponent } from './pages/pet-profile/pet-profile.component';
import { AboutUsComponent } from './pages/about-us/about-us.component';
import { ContactUsComponent } from './pages/contact-us/contact-us.component';
import { NotFoundComponent } from './pages/not-found/not-found.component';
import { DonationComponent } from './pages/donation/donation.component';
import { InterviewFormComponent} from './components/interview-form//interview-form.component';
import { EditAnimalComponent } from './components/edit-animal/edit-animal.component';
import { EditShelterComponent } from './components/edit-shelter/edit-shelter.component';
import { PetsManagementComponent } from './pages/pets-management/pets-management.component';
import { AccessDeniedComponent } from './pages/access-denied/access-denied.component';
import { AuthGuard } from './guards/auth.guard';
import { ShelterGuard } from './guards/shelter.guard';

export const routes: Routes = [
    {
        path: 'home',
        component: HomeComponent,
        title: 'Home'
    },
    {
        path: '',
        redirectTo: 'home',
        pathMatch: 'full'
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
        title: 'View Admin',
        canActivate: [AuthGuard],
        data: { requiresAdmin: true }
    },
    {
        path: 'register-animal',
        component: RegisterAnimalComponent,
        title: 'Register Animal',
        canActivate: [ShelterGuard]
    },
    {
        path: 'shelter-profile/:id',
        component: ShelterProfileComponent,
        title: 'Pagina de perfil de refugio'
    },
    { 
        path: 'animal-profile/:id',
        component: PetProfileComponent,
        title: 'Pagina de perfil de los animales'
    },
    {
        path: 'about-us',
        component: AboutUsComponent,
        title: 'About Us'
    },
    {
        path: 'contacto',
        component: ContactUsComponent,
        title: 'Contacto'
    },
    {
        path: 'donation',
        component: DonationComponent,
        title: 'Donacion'
    },
    {
        path: 'interview-form/:id',
        component: InterviewFormComponent,
        title: 'Formulario de Entrevista'
    },
    {
        path: 'edit-animal/:id',
        component: EditAnimalComponent,
        title: 'Editar Animal',
        canActivate: [ShelterGuard]
    },
    {
        path: 'edit-shelter/:id',
        component: EditShelterComponent,
        title: 'Editar Refugio',
        canActivate: [ShelterGuard]
    },
    {
        path: 'pets-management',
        component: PetsManagementComponent,
        title: 'Gestión de Mascotas',
        canActivate: [ShelterGuard]
    },
    {
        path: 'access-denied',
        component: AccessDeniedComponent,
        title: 'Acceso Denegado'
    },
    { 
        path: '**', 
        component: NotFoundComponent,
        title: 'Page Not Found'
    }
];
