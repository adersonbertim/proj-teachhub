import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { CommonModule, Location } from '@angular/common';
import { DomSanitizer, SafeHtml } from '@angular/platform-browser';
import { PostagemService } from '../../../services/postagem.service';
import { LikeStatusDTO, PostagemDTO, ComentarioDTO, ApiResponse } from '../../../services/model.service';
import { MaterialModule } from '../../../material-module';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-postagem-detalhe',
  imports: [CommonModule, MaterialModule, RouterLink, FormsModule],
  templateUrl: './postagem-detalhe.component.html',
  styleUrl: './postagem-detalhe.component.scss'
})
export class PostagemDetalheComponent implements OnInit {
  postagem?: PostagemDTO;
  descricaoSegura?: SafeHtml;
  carregando = true;
  erro = false;
  relacionadas: PostagemDTO[] = [];
  likeStatus: LikeStatusDTO = { qtdLikes: 0, curtidoPeloUsuario: false };
  favoritado = false;
  comentarios : ComentarioDTO[] = [];
  novoComentario = '';
  enviandoComentario = false;
  
  


  constructor(
    private route: ActivatedRoute,
    private location: Location,
    private postagemService: PostagemService,
    private sanitizer: DomSanitizer
  ) {}

  ngOnInit() {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    if (isNaN(id)) {
      this.erro = true;
      this.carregando = false;
      return;
    }
    this.carregarPostagem(id);
  }

  carregarPostagem(id: number) {
    this.postagemService.getPostagemById(id).subscribe({
      next: (response) => {
        this.postagem = response.data;
        this.descricaoSegura = this.sanitizer.bypassSecurityTrustHtml(this.postagem.descricao || '');
        this.carregando = false;

        this.carregarLikeStatus(id);
        this.carregarFavoritoStatus(id);
        this.carregarComentarios(id);
        this.carregarRelacionadas(id);
      },
      error: (err) => {
        console.error('Erro ao carregar postagem:', err);
        this.erro = true;
        this.carregando = false;
      }
    });
  }
  

  // Likes
  carregarLikeStatus(id: number) {
    this.postagemService.getLikeStatus(id).subscribe({
      next: (res) => (this.likeStatus = res.data),
      error: (err) => console.error('Erro ao carregar likes:', err)
    });
  }

  estadoLikePostagem() {
    if (!this.postagem) return;

    this.postagemService.estadoLikePostagem(this.postagem.id).subscribe({
      next: (res) => (this.likeStatus = res.data),
      error: (err) => console.error('Erro ao curtir postagem:', err)
    });
  }


  // Favoritos
  carregarFavoritoStatus(id: number) {
    this.postagemService.getFavoritoStatus(id).subscribe({
      next: (res) => (this.favoritado = res.data),
      error: (err) => console.error('Erro ao carregar favorito:', err)
    });
  }

  estadoFavorito() {
    if (!this.postagem) return;

    this.postagemService.estadoFavorito(this.postagem.id).subscribe({
      next: (res) => (this.favoritado = res.data),
      error: (err) => console.error('Erro ao favoritar:', err)
    });
  }

  //Comentarios
   carregarComentarios(id: number) {
    this.postagemService.getComentarios(id).subscribe({
      next: (res) => (this.comentarios = res.data),
      error: (err) => console.error('Erro ao carregar comentários:', err)
    });
  }

  enviarComentario() {
    if (!this.novoComentario.trim() || !this.postagem) return;

    this.enviandoComentario = true;
    this.postagemService.enviarComentario(this.postagem.id, this.novoComentario).subscribe({
      next: (res) => {
        this.comentarios.unshift(res.data);
        this.novoComentario = '';
        this.enviandoComentario = false;
      },
      error: (err) => {
        console.error('Erro ao enviar comentário:', err);
        this.enviandoComentario = false;
      }
    });
  }

  // Abre/fecha o campo de resposta de um comentário
  estadoResposta(comentario: ComentarioDTO) {
    comentario.respondendo = !comentario.respondendo;
  }

  enviarResposta(comentario: ComentarioDTO) {
    if (!comentario.novaResposta?.trim()) return;

    this.postagemService.enviarResposta(comentario.id, comentario.novaResposta).subscribe({
      next: (res) => {
        comentario.respostas = res.data.respostas;
        comentario.novaResposta = '';
        comentario.respondendo = false;
        comentario.mostrandoRespostas = true;
      },
      error: (err) => console.error('Erro ao responder comentário:', err)
    });
  }

  estadoLikeComentario(comentario: ComentarioDTO) {
    this.postagemService.estadoLikeComentario(comentario.id).subscribe({
      next: (res) => {
        comentario.qtdLikes = res.data.qtdLikes;
        comentario.curtidoPeloUsuario = res.data.curtidoPeloUsuario;
      },
      error: (err) => console.error('Erro ao curtir comentário:', err)
    });
  }

   carregarRelacionadas(id: number) {
    this.postagemService.getRelacionadas(id).subscribe({
      next: (res) => (this.relacionadas = res.data),
      error: (err) => console.error('Erro ao carregar relacionadas:', err)
    });
  }

  voltar() {
    this.location.back();
  }
}