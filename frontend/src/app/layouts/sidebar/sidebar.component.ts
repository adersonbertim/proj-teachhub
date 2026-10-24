import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { MaterialModule } from '../../material-module';
import { AuthService } from '../../core/services/auth/auth.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-sidebar',
  standalone: true,
  imports: [RouterModule, CommonModule, MaterialModule,],
  templateUrl: './sidebar.component.html',
  styleUrl: './sidebar.component.scss'
})
export class SidebarComponent {
  constructor(private router : Router, private authService: AuthService){}

  @Input() isSidebarCollapsed: boolean = false;

  navItens = [
  { label: 'Home', icon: 'grid_view', route: '/home' },
  { label: 'Explorar', icon: 'explore', route: '/postagens' },
  { label: 'Criar Postagem', icon: 'edit_square', route: '/criar-postagem' },
  { label: 'Cursos', icon: 'school', route: '/cursos' },
  { label: 'Falar com IA', icon: 'smart_toy', route: '/ia' },
  { label: 'Meu Perfil', icon: 'account_circle', route: '/perfil' }
];


  sair() {
    Swal.fire({
  title: "Tem certeza?",
  text: "Você será desconectado.",
  icon: "question",
  showCancelButton: true,
  confirmButtonColor: "#1976d2",
  confirmButtonText: "Sim, sair"
}).then((result) => {
  if (result.isConfirmed) {
    this.authService.logout();
    this.router.navigate(['/login']);
    Swal.fire({
      title: "Desconectado!",
      text: "Você foi desconectado com sucesso.",
      icon: "success"
    });
  }
});

  }

}
