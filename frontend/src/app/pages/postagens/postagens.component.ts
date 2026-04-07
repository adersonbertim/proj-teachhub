import { Component } from '@angular/core';
import { MaterialModule } from '../../material-module';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { PostagemDTO } from '../../services/model.service';

@Component({
  selector: 'app-postagens',
  imports: [MaterialModule, CommonModule, FormsModule ],
  templateUrl: './postagens.component.html',
  styleUrl: './postagens.component.scss'
})
export class PostagensComponent {
posts: any;

  constructor(private router: Router) { }

  navigateToCreatePost() {
    this.router.navigate(['/criar-postagem']);
  }

  postagens : PostagemDTO[] = [];

  favoritarPostagem(id: number) {
    const postagem = this.postagens.find(post => post.idPostagem === id);
    if (postagem) {
      postagem.isFavorita = !postagem.isFavorita;
    }
  };

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

}
