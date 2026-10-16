import { Routes } from '@angular/router';
import { AuthLayoutComponent } from './layouts/auth-layout/auth-layout.component';
import { MainLayoutComponent } from './layouts/main-layout/main-layout.component';
import { authGuard } from './guards/auth.guard';
import { adminGuard } from './guards/admin.guard';
import { guestGuard } from './guards/guest.guard';


export const routes: Routes = [
    // telas de login e cadastro
    {
        path: '',
        component: AuthLayoutComponent,
        children: [
            { path: '', redirectTo: 'login', pathMatch: 'full' },
            {
                path: 'login',
                canActivate: [guestGuard],
                title: 'Entrar | TeachHub',
                loadComponent: () => import('./pages/auth/login/login.component').then(m => m.LoginComponent)
            },
            {
                path: 'register',
                canActivate: [guestGuard],
                title: 'Cadastro | TeachHub',
                loadComponent: () => import('./pages/auth/register/register.component').then(m => m.RegisterComponent)
            }
        ]
    },

    // telas com menu lateral
    {
        path: '',
        component: MainLayoutComponent,
        children: [
            {
                path: 'home',
                title: 'Início | TeachHub',
                loadComponent: () => import('./pages/home/home.component').then(m => m.HomeComponent)
            },

            {
                path: 'postagens',
                title: 'Explorar | TeachHub',
                loadComponent: () => import('./pages/postagens/postagens.component').then(m => m.PostagensComponent)
            },
            {
                path: 'postagens/:id',
                title: 'Postagem | TeachHub',
                loadComponent: () => import('./pages/postagens/postagem-detalhe/postagem-detalhe.component').then(m => m.PostagemDetalheComponent)
            },
            {
                path: 'criar-postagem',
                canActivate: [authGuard],
                title: 'Criar postagem | TeachHub',
                loadComponent: () => import('./pages/postagens/criar-postagem/criar-postagem.component').then(m => m.CriarPostagemComponent)
            },

            {
                path: 'cursos',
                title: 'Cursos | TeachHub',
                loadComponent: () => import('./pages/cursos/cursos.component').then(m => m.CursosComponent)
            },
            {
                path: 'cursos/gerenciar',
                canActivate: [authGuard, adminGuard],
                title: 'Curadoria de cursos | TeachHub',
                loadComponent: () => import('./pages/cursos/cursos-gerenciar/cursos-gerenciar.component').then(m => m.CursosGerenciarComponent)
            },
            {
                path: 'cursos/:id',
                title: 'Curso | TeachHub',
                loadComponent: () => import('./pages/cursos/curso-detalhe/curso-detalhe.component').then(m => m.CursoDetalheComponent)
            },

            {
                path: 'perfil',
                canActivate: [authGuard],
                title: 'Meu perfil | TeachHub',
                loadComponent: () => import('./pages/perfil/perfil-visitante/perfil-visitante.component').then(m => m.PerfilVisitanteComponent)
            },
            {
                path: 'perfil/config',
                canActivate: [authGuard],
                title: 'Editar perfil | TeachHub',
                loadComponent: () => import('./pages/perfil/perfil-config/perfil-config.component').then(m => m.PerfilConfigComponent)
            },
            {
                path: 'perfil/:id',
                title: 'Perfil | TeachHub',
                loadComponent: () => import('./pages/perfil/perfil-visitante/perfil-visitante.component').then(m => m.PerfilVisitanteComponent)
            },

            {
                path: 'ia',
                canActivate: [authGuard],
                title: 'Assistente IA | TeachHub',
                loadComponent: () => import('./pages/ia-chat/ia-chat.component').then(m => m.IaChatComponent)
            },

            {
                path: '**',
                title: 'Página não encontrada | TeachHub',
                loadComponent: () => import('./pages/not-found/not-found.component').then(m => m.NotFoundComponent)
            }
        ]
    }
];