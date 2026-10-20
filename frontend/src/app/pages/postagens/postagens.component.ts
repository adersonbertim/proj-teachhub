import { Component, OnInit } from '@angular/core';
import { MaterialModule } from '../../material-module';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { PostagemDTO } from '../../services/model.service';
import { PageEvent } from '@angular/material/paginator';
import { PostagemService } from '../../services/postagem.service';
import { FavoritoService } from '../../services/favorito.service';

@Component({
  selector: 'app-postagens',
  imports: [MaterialModule, CommonModule, FormsModule, RouterLink],
  templateUrl: './postagens.component.html',
  styleUrl: './postagens.component.scss'
})
export class PostagensComponent implements OnInit {
  pageIndex = 0;
  pageSize = 10;
  totalPostagens = 0;
  termoBusca = '';
  carregandoFeed = false;

  postagensExibidas: PostagemDTO[] = [];
  postagens: PostagemDTO[] = [];

  constructor(
    private router: Router,
    private postagemService: PostagemService,
    private favoritoService: FavoritoService,
  ) {}

  ngOnInit() {
    this.carregarPagina();
  }

  navegarCriarPostagem() {
    this.router.navigate(['/criar-postagem']);
  }

  favoritarPostagem(postagemId: number) {
    this.favoritoService.toggleFavorito(postagemId).subscribe({
      next: (res) => {
        const post = this.postagens.find(p => p.idPostagem === postagemId);
        if (post) post.isFavorita = res.data;

        const postExibida = this.postagensExibidas.find(p => p.idPostagem === postagemId);
        if (postExibida) postExibida.isFavorita = res.data;
      },
      error: (err) => console.error('Erro ao favoritar:', err)
    });
  }

  notaPostagem(id: number, tipo: 'like' | 'dislike') {
    const postagem = this.postagens.find(post => post.idPostagem === id);
    if (!postagem) return;

    if (tipo === 'like') {
      postagem.likes = (postagem.likes || 0) + 1;
    } else {
      postagem.dislikes = (postagem.dislikes || 0) + 1;
    }
  }

  detalhesPostagens(id: number) {
    this.router.navigate(['/postagens', id]);
  }

  pesquisar() {
    if (this.carregandoFeed) return;
    this.pageIndex = 0;
    this.carregarPagina();
  }

  mudancaPagina(event: PageEvent) {
    this.pageIndex = event.pageIndex;
    this.pageSize = event.pageSize;
    this.carregarPagina();
  }

  private carregarPagina() {
    this.carregandoFeed = true;
    const termo = this.termoBusca.trim();
    const requisicao = termo
      ? this.postagemService.buscarPostagens(termo, this.pageIndex, this.pageSize)
      : this.postagemService.listarFeed(this.pageIndex, this.pageSize);

    requisicao.subscribe({
      next: (res) => {
        this.postagens = res.data.content;
        this.postagensExibidas = res.data.content;
        this.totalPostagens = res.data.totalElements;
        this.carregandoFeed = false;
      },
      error: (err) => {
        console.error('Erro ao carregar postagens:', err);
        this.postagens = [];
        this.postagensExibidas = [];
        this.totalPostagens = 0;
        this.carregandoFeed = false;
      },
    });
  }
}
