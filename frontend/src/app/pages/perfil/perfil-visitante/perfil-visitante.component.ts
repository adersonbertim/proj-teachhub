import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';
import { Observable } from 'rxjs';
import { MaterialModule } from '../../../material-module';
import { PerfilService } from '../../../services/perfil.service';
import { ApiResponse, Perfil } from '../../../services/model.service';

@Component({
  selector: 'app-perfil-visitante',
  imports: [CommonModule, MaterialModule, RouterLink],
  templateUrl: './perfil-visitante.component.html',
  styleUrl: './perfil-visitante.component.scss'
})
export class PerfilVisitanteComponent implements OnInit {
  perfil?: Perfil;
  carregando = true;
  erro = false;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private perfilService: PerfilService
  ) {}

  ngOnInit() {
    const idParam = this.route.snapshot.paramMap.get('id');

    if (!idParam) {
      this.carregarPerfil(this.perfilService.buscarMeuPerfil());
      return;
    }

    const id = Number(idParam);
    if (isNaN(id)) {
      this.erro = true;
      this.carregando = false;
      return;
    }

    this.carregarPerfil(this.perfilService.buscarPerfil(id));
  }

  private carregarPerfil(request: Observable<ApiResponse<Perfil>>) {
    request.subscribe({
      next: (response) => {
        this.perfil = response.data;
        this.carregando = false;
      },
      error: (err) => {
        console.error('Erro ao carregar perfil:', err);
        this.erro = true;
        this.carregando = false;
      }
    });
  }

  editarPerfil() {
    this.router.navigate(['/perfil/config']);
  }

  compartilharPerfil() {
    const url = window.location.href;
    if (navigator.share) {
      navigator.share({ title: this.perfil?.nome, url }).catch(() => {});
    } else {
      navigator.clipboard.writeText(url);
    }
  }
}