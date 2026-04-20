import { Routes } from '@angular/router';
import { AuthLayoutComponent } from './layouts/auth-layout/auth-layout.component';
import { LoginComponent } from './pages/auth/login/login.component';
import { RegisterComponent } from './pages/auth/register/register.component';
import { MainLayoutComponent } from './layouts/main-layout/main-layout.component';
import { IaChatComponent } from './pages/ia-chat/ia-chat.component';
import { HomeComponent } from './pages/home/home.component';
import { CriarPostagemComponent } from './pages/criar-postagem/criar-postagem.component';
import { PerfilComponent } from './pages/perfil/perfil.component';
import path from 'path/win32';
import { SidebarComponent } from './layouts/sidebar/sidebar.component';
import { PostagensComponent } from './pages/postagens/postagens.component';
import { CursosComponent } from './pages/cursos/cursos.component';
import { PostagemDetalheComponent } from './pages/postagem-detalhe/postagem-detalhe.component';


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
    
    
    {
        path: '',
        component: MainLayoutComponent,
        // NEED: canActivate for routes
        children: [
            {path: 'home', component: HomeComponent},
            {path: 'criar-postagem', component: CriarPostagemComponent},
            {path: 'perfil', component: PerfilComponent},
            {path: 'ia', component: IaChatComponent},
            {path: 'postagens', component: PostagensComponent},
            {path: 'cursos', component: CursosComponent},
            {path:'**', redirectTo: 'home'},
            {path: 'postagens/:id', component: PostagemDetalheComponent} 
        ]

    }
];
