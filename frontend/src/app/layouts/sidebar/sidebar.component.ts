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
    { label: 'Home', icon: 'home', route: '/home' },
    { label: 'Nova Postagem', icon: 'add_circle', route: '/criar-postagem' },
    { label: 'Cursos', icon: 'school', route: '/cursos' },
    { label: 'Assistente IA', icon: 'robot_2', route: '/ia' },
    { label: 'Meu Perfil', icon: 'account_circle', route: '/perfil' },
  ];

  logout(){ 
    console.log('Logout');
  
  }

}
