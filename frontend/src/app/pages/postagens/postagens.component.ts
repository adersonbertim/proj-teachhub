import { Component, OnInit } from '@angular/core';
import { MaterialModule } from '../../material-module';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { PostagemDTO } from '../../services/model.service';
import { PageEvent } from '@angular/material/paginator';
import { PostagemService } from '../../services/postagem.service';
import { FavoritoService } from '../../services/favorito.service';
import Swal from 'sweetalert2';
import { MatChipListboxChange } from '@angular/material/chips';


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
  filtro: 'recentes' | 'emAlta' | 'minhas' = 'recentes';
  erroFeed = '';
  esperaBusca?: ReturnType<typeof setTimeout>;

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
  if (!localStorage.getItem('teachhub_token')) {
    Swal.fire({ title: 'Entre na sua conta', text: 'Faça login para salvar postagens.', icon: 'info', confirmButtonText: 'OK' });
    return;
  }
  this.favoritoService.toggleFavorito(postagemId).subscribe({
    next: (res) => {
      const post = this.postagens.find(p => p.idPostagem === postagemId);
      if (post) post.isFavorita = res.data;

      const postExibida = this.postagensExibidas.find(p => p.idPostagem === postagemId);
      if (postExibida) postExibida.isFavorita = res.data;
    },
    error: (err) => {
      if (err.status !== 401) Swal.fire('Erro', err.error?.message || 'Não foi possível salvar a postagem.', 'error');
    }
  });
}


resumoTexto(html: string): string {
  return (html || '')
    .replace(/<[^>]+>/g, ' ')
    .replace(/&nbsp;/g, ' ')
    .replace(/&lt;/g, '<')
    .replace(/&gt;/g, '>')
    .replace(/&quot;/g, '"')
    .replace(/&#39;/g, "'")
    .replace(/&amp;/g, '&')
    .replace(/\s+/g, ' ')
    .trim();
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

buscarAoDigitar() {
  clearTimeout(this.esperaBusca);
  this.esperaBusca = setTimeout(() => this.pesquisar(), 400);
}

pesquisar() {
  clearTimeout(this.esperaBusca);
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
  this.erroFeed = '';
  const termo = this.termoBusca.trim();

  
  if (!termo && this.filtro === 'minhas') {
    this.postagemService.listarMinhas().subscribe({
      next: (res) => {
        const inicio = this.pageIndex * this.pageSize;
        this.postagens = res.data;
        this.postagensExibidas = res.data.slice(inicio, inicio + this.pageSize);
        this.totalPostagens = res.data.length;
        this.carregandoFeed = false;
      },
      error: () => {
        this.erroFeed = 'Não foi possível carregar as suas postagens.';
        this.postagens = [];
        this.postagensExibidas = [];
        this.totalPostagens = 0;
        this.carregandoFeed = false;
      },
    });
    return;
  }

  let requisicao = this.postagemService.listarFeed(this.pageIndex, this.pageSize);
  if (termo) {
    requisicao = this.postagemService.buscarPostagens(termo, this.pageIndex, this.pageSize);
  } else if (this.filtro === 'emAlta') {
    requisicao = this.postagemService.listarEmAlta(this.pageIndex, this.pageSize);
  }

  requisicao.subscribe({
    next: (res) => {
      this.postagens = res.data.content;
      this.postagensExibidas = res.data.content;
      this.totalPostagens = res.data.totalElements;
      this.carregandoFeed = false;
    },
    error: (err) => {
      console.error('Erro ao carregar postagens:', err);
      this.erroFeed = 'Não foi possível carregar as postagens. Tente novamente.';
      this.postagens = [];
      this.postagensExibidas = [];
      this.totalPostagens = 0;
      this.carregandoFeed = false;
    },
  });
}

  mudarFiltro(evento: MatChipListboxChange) {
    if (!evento.value) {
    evento.source.value = this.filtro;
    return;
  }
    if (evento.value === 'minhas' && !localStorage.getItem('teachhub_token')) {
      evento.source.value = this.filtro;
      Swal.fire({ title: 'Entre na sua conta', text: 'Faça login para ver as suas postagens.', icon: 'info', confirmButtonText: 'OK' });
      return;
    }
  this.filtro = evento.value;
  this.termoBusca = '';
  this.pageIndex = 0;
  this.carregarPagina();
}


}
