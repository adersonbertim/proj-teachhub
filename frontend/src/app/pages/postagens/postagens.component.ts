import { Component } from '@angular/core';
import { MaterialModule } from '../../material-module';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { PostagemDTO } from '../../services/model.service';
import { PageEvent } from '@angular/material/paginator';
import { PostagemService } from '../../services/postagem.service';


@Component({
  selector: 'app-postagens',
  imports: [MaterialModule, CommonModule, FormsModule, RouterLink],
  templateUrl: './postagens.component.html',
  styleUrl: './postagens.component.scss'
})
export class PostagensComponent {

  //this need to fix :)
  posts: any;
  pageIndex: number = 0;
  pageSize: number = 10;

  constructor(private router: Router, private postagemService: PostagemService) { }
// criar postagem
  navegarCriarPostagem() {
    this.router.navigate(['/criar-postagem']);
  }

  //array das postagens
  postagensExibidas: PostagemDTO[] = [];
  postagens : PostagemDTO[] = [];



//nao pode mecher nessas ainda!!! 
  favoritarPostagem(id: number) {
    const postagem = this.postagens.find(post => post.idPostagem === id);
    if (postagem) {
      postagem.isFavorita = !postagem.isFavorita;
    }
  };


  //nao pode mecher nessas ainda, é teste
  notaPostagem(id: number, tipo : 'like' | 'dislike') {
    const postagem = this.postagens.find(post => post.idPostagem === id);
    if (postagem) {
      if (tipo === 'like') {
        postagem.likes = (postagem.likes || 0) + 1;
      } else {
        postagem.dislikes = (postagem.dislikes || 0) + 1;
      }
    }
  };


    // navegando para detalhes da postagem
  detalhesPostagens(id: number) {
    this.router.navigate(['/postagens', id]);
  }


  //testing
  
  mudancaPagina(event: PageEvent) {
    this.pageIndex = event.pageIndex;
    this.pageSize = event.pageSize;
    this.atualizarPagina();
  }

  ngOnInit() {
  this.postagemService.listarFeed().subscribe((res: any) => {
    this.postagens = res.data; // depende do ApiResponse
    this.atualizarPagina();
  });
}
  
  atualizarPagina() {
    const inicio = this.pageIndex * this.pageSize;
    const fim = inicio + this.pageSize;
    // Lógica para atualizar a página, como recarregar os dados ou resetar o estado
    this.postagensExibidas = this.postagens.slice(inicio, fim);
  }


  
}
