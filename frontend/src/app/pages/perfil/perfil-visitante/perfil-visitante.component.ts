import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';
import { Observable } from 'rxjs';
import { MaterialModule } from '../../../material-module';
import { PerfilService } from '../../../services/perfil.service';
import { PostagemService } from '../../../services/postagem.service';
import { ApiResponse, FavoritoDTO, Perfil } from '../../../services/model.service';
import Swal from 'sweetalert2';
import { FavoritoService } from '../../../services/favorito.service';

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
  favoritos: FavoritoDTO[] = [];

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private perfilService: PerfilService,
    private postagemService: PostagemService,
    private favoritoService: FavoritoService
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

       if (this.perfil.souDono) {
          this.favoritoService.listarMeus().subscribe({
            next: (res) => this.favoritos = res.data || [],
            error: (err) => console.error('Erro ao carregar favoritos:', err)
          });
        }
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

  excluirPostagem(id: number) {
    Swal.fire({
      title: 'Tem certeza?',
      text: 'Esta ação não pode ser desfeita.',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#1976d2',
      cancelButtonColor: '#ccc',
      confirmButtonText: 'Sim, excluir!',
      cancelButtonText: 'Cancelar'
    }).then((result) => {
      if (result.isConfirmed) {
        this.postagemService.deletarPostagem(id).subscribe({
          next: () => {
            if (this.perfil?.postagens) {
              this.perfil.postagens = this.perfil.postagens.filter(p => p.idPostagem !== id);
            }
            this.favoritos = this.favoritos.filter(f => f.idPostagem !== id);
          },
          error: (err) => {
            console.error('Erro ao excluir postagem:', err);
            Swal.fire('Erro', 'Não foi possível excluir a postagem.', 'error');
          }
        });
      }
    });
  }

  compartilharPerfil() {
    const url = window.location.href;
    if (navigator.share) {
      navigator.share({ title: this.perfil?.nome, url }).catch(() => {});
    } else {
      navigator.clipboard.writeText(url);
    }
  }

desfavoritar(favorito: FavoritoDTO) {
  this.favoritoService.toggleFavorito(favorito.idPostagem).subscribe({
    next: () => {
      this.favoritos = this.favoritos.filter(f => f.idFavorito !== favorito.idFavorito);
    },
    error: (err) => {
      console.error('Erro ao desfavoritar:', err);
      Swal.fire('Erro', 'Não foi possível remover dos favoritos.', 'error');
    }
  });
}
    private carregarFavoritos() {
    this.favoritoService.listarMeus().subscribe({
      next: (res) => this.favoritos = res.data,
      error: (err) => console.error('Erro ao carregar favoritos:', err)
    });
  }
  

}