import { Routes } from '@angular/router';
import { AuthLayoutComponent } from './layouts/auth-layout/auth-layout.component';
import { LoginComponent } from './pages/auth/login/login.component';
import { RegisterComponent } from './pages/auth/register/register.component';
import { MainLayoutComponent } from './layouts/main-layout/main-layout.component';
import { IaChatComponent } from './pages/ia-chat/ia-chat.component';
import { HomeComponent } from './pages/home/home.component';
import { CriarPostagemComponent } from './pages/criar-postagem/criar-postagem.component';
import { PerfilComponent } from './pages/perfil/perfil.component';
import { PostagemComponent } from './pages/postagem/postagem.component';

export const routes: Routes = [
    //group for autentication
    {
        path: '',
        component: AuthLayoutComponent,
        children: [
            {path: 'login', component: LoginComponent},
            {path: 'register', component: RegisterComponent},
            {path: '', redirectTo: 'login', pathMatch: 'full'} // redirect root to login

        ]
    },
    //group for navbar, sidebar, principal layout 
    {
        path: '',
        component: MainLayoutComponent,
        // NEED: canActivate for routes
        children: [
            {path: 'home', component: HomeComponent},
            {path: 'postagens', component: PostagemComponent},
            {path: 'criar-postagem', component: CriarPostagemComponent},
            {path: 'perfil', component: PerfilComponent}
        ]

    }
];
