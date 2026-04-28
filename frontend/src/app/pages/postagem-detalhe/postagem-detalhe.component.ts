import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { PostagemService } from '../../services/postagem.service';
import { PostagemDTO } from '../../services/model.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MaterialModule } from '../../material-module';

@Component({
  selector: 'app-postagem-detalhe',
  imports: [CommonModule, FormsModule, MaterialModule],
  templateUrl: './postagem-detalhe.component.html',
  styleUrl: './postagem-detalhe.component.scss'
})
export class PostagemDetalheComponent implements OnInit {
  postagem?: PostagemDTO;

  constructor(
    private route : ActivatedRoute, 
    private postagemService: PostagemService
  ) { }

  ngOnInit() {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.postagem = this.postagemService.getPostagemById(id);
  }
}
