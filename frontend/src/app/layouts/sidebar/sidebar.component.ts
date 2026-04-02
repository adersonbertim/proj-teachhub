import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { MaterialModule } from '../../material-module';

@Component({
  selector: 'app-sidebar',
  standalone: true,
  imports: [RouterModule, CommonModule, MaterialModule,],
  templateUrl: './sidebar.component.html',
  styleUrl: './sidebar.component.scss'
})
export class SidebarComponent {
  constructor(private router : Router){}

  @Input() isSidebarCollapsed: boolean = false;

  navItens = [
  { label: 'Home', icon: 'grid_view', route: '/home' },
  { label: 'Explorar', icon: 'explore', route: '/postagens' },
  { label: 'Criar Postagem', icon: 'edit_square', route: '/criar-postagem' },
  { label: 'Cursos', icon: 'school', route: '/cursos' },
  { label: 'Falar com IA', icon: 'smart_toy', route: '/ia' },
  { label: 'Meu Perfil', icon: 'account_circle', route: '/perfil' }
];


  logout(){ 
    console.log('Logout');
  
  }

}
