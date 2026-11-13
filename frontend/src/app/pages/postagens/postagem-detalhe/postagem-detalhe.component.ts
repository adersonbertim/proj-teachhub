import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { CommonModule, Location } from '@angular/common';
import { PostagemService } from '../../../services/postagem.service';
import { LikeStatusDTO, PostagemDTO, ComentarioDTO, ApiResponse } from '../../../services/model.service';
import { MaterialModule } from '../../../material-module';
import { FormsModule } from '@angular/forms';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-postagem-detalhe',
  imports: [CommonModule, MaterialModule, RouterLink, FormsModule],
  templateUrl: './postagem-detalhe.component.html',
  styleUrl: './postagem-detalhe.component.scss'
})
export class PostagemDetalheComponent implements OnInit {
  postagem?: PostagemDTO;
  postagemId!: number;
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
  
  ) {}

ngOnInit() {
  const id = Number(this.route.snapshot.paramMap.get('id'));
  if (isNaN(id)) {
    this.erro = true;
    this.carregando = false;
    return;
  }
  this.postagemId = id;
  this.carregarPostagem(id);
}

  carregarPostagem(id: number) {
    this.postagemService.getPostagemById(id).subscribe({
      next: (response) => {
        this.postagem = response.data;
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
    if (!this.postagem || this.precisaEntrar()) return;

    this.postagemService.estadoLikePostagem(this.postagemId!).subscribe({
      next: (res) => (this.likeStatus = res.data),
      error: (err) => console.error('Erro ao curtir postagem:', err)
    });
  }


  // Favoritos
  carregarFavoritoStatus(id: number) {
     if (!localStorage.getItem('teachhub_token')) return;
    this.postagemService.getFavoritoStatus(id).subscribe({
      next: (res) => (this.favoritado = res.data),
      error: (err) => console.error('Erro ao carregar favorito:', err)
    });
  }

  estadoFavorito() {
    if (!this.postagem) return;

    this.postagemService.estadoFavorito(this.postagemId!).subscribe({
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
    this.postagemService.enviarComentario(this.postagemId, this.novoComentario).subscribe({
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
    if (this.precisaEntrar()) return;
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
      next: (res) => (this.relacionadas = res.data.content),
      error: (err) => console.error('Erro ao carregar relacionadas:', err)
    });
  }

  voltar() {
    this.location.back();
  }

  private precisaEntrar(): boolean {
  if (localStorage.getItem('teachhub_token')) return false;
  Swal.fire({
    title: 'Entre na sua conta',
    text: 'Faça login para curtir, salvar ou comentar.',
    icon: 'info',
    confirmButtonText: 'OK'
  });
  return true;
}

private avisarErro(err: any, mensagem: string) {
  if (err.status === 401) return;
  Swal.fire('Erro', err.error?.message || mensagem, 'error');
}

excluirComentario(comentario: ComentarioDTO) {
  Swal.fire({
    title: 'Excluir comentário?',
    text: 'As respostas dele também serão apagadas.',
    icon: 'warning',
    showCancelButton: true,
    confirmButtonColor: '#1976d2',
    confirmButtonText: 'Sim, excluir',
    cancelButtonText: 'Cancelar'
  }).then((result) => {
    if (!result.isConfirmed) return;

    this.postagemService.excluirComentario(comentario.id).subscribe({
      next: () => this.carregarComentarios(this.postagemId),
      error: (err) => Swal.fire('Erro', err.error?.message || 'Não foi possível excluir o comentário.', 'error')
    });
  });
}

}
