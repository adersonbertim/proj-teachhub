import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { CommonModule, Location } from '@angular/common';
import { DomSanitizer, SafeHtml } from '@angular/platform-browser';
import { PostagemService } from '../../../services/postagem.service';
import { PostagemDTO } from '../../../services/model.service';
import { MaterialModule } from '../../../material-module';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-postagem-detalhe',
  imports: [CommonModule, MaterialModule, RouterLink, FormsModule],
  templateUrl: './postagem-detalhe.component.html',
  styleUrl: './postagem-detalhe.component.scss'
})
export class PostagemDetalheComponent implements OnInit {
likeStatus: any;
favoritado: any;
novoComentario: any;
enviandoComentario: unknown;
comentarios: any;
relacionadas: any;
toggleLikePostagem() {
throw new Error('Method not implemented.');
}
toggleFavoritar() {
throw new Error('Method not implemented.');
}
enviarComentario() {
throw new Error('Method not implemented.');
}
toggleResponder(_t66: any) {
throw new Error('Method not implemented.');
}
toggleLikeComentario(_t98: any) {
throw new Error('Method not implemented.');
}
enviarResposta(_t66: any) {
throw new Error('Method not implemented.');
}
  postagem?: PostagemDTO;
  descricaoSegura?: SafeHtml;
  carregando = true;
  erro = false;

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

    this.postagemService.getPostagemById(id).subscribe({
      next: (response) => {
        this.postagem = response.data;
        this.descricaoSegura = this.sanitizer.bypassSecurityTrustHtml(this.postagem.descricao || '');
        this.carregando = false;
      },
      error: (err) => {
        console.error('Erro ao carregar postagem:', err);
        this.erro = true;
        this.carregando = false;
      }
    });
  }

  voltar() {
    this.location.back();
  }
}