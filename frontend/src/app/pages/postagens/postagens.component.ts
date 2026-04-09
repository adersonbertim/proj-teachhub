import { Component } from '@angular/core';
import { MaterialModule } from '../../material-module';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { PostagemDTO } from '../../services/model.service';
import { PageEvent } from '@angular/material/paginator';

@Component({
  selector: 'app-postagens',
  imports: [MaterialModule, CommonModule, FormsModule ],
  templateUrl: './postagens.component.html',
  styleUrl: './postagens.component.scss'
})
export class PostagensComponent {

  //this need to fix :)
posts: any;
  pageIndex: number = 0;
  pageSize: number = 10;

  constructor(private router: Router) { }

  navigateToCreatePost() {
    this.router.navigate(['/criar-postagem']);
  }
  postagensExibidas: PostagemDTO[] = [];
  postagens : PostagemDTO[] = [];

  //testando
  carregarDadosIniciais() {
  const nomes = ['Maria Rocha', 'Luis Silva', 'Marcos Dutra', 'Ana Julia'];
  const materias = ['Matemática', 'Física', 'História', 'Ciências'];

  for (let i = 1; i <= 25; i++) {
    this.postagens.push({
      idPostagem: i,
      // O símbolo % (módulo) ajuda a alternar entre os nomes da lista acima
      autor: nomes[i % nomes.length], 
      titulo: `Postagem Educativa #${i}`,
      descricao: `Este é um resumo automático da postagem número ${i} para testarmos como o Angular Material se comporta com muitos cards na tela.`,
      likes: Math.floor(Math.random() * 50), // Gera curtidas aleatórias
      dislikes: Math.floor(Math.random() * 10),
      isFavorita: false,
      isPlanoAula: i % 2 === 0, // Alterna entre true e false
      nota: true, // Aqui você pode mudar para number depois se quiser estrelas
      materia: materias[i % materias.length],
      categoria: 'Geral',
      conteudo: '<p>Conteúdo rico aqui...</p>',
      dataCriacao: new Date().toISOString()
    });
  }
}


//nao pode mecher nessas ainda!!! 
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

    getPostagemById(id: number) {
    return this.postagens.find(post => post.idPostagem === id);
  }

  //testing
  
  onPageChange(event: PageEvent) {
    this.pageIndex = event.pageIndex;
    this.pageSize = event.pageSize;
    this.atualizarPagina();
  }

  ngOnInit() {
    this.carregarDadosIniciais();
    this.atualizarPagina();
    };
  
  atualizarPagina() {
    const inicio = this.pageIndex * this.pageSize;
    const fim = inicio + this.pageSize;
    // Lógica para atualizar a página, como recarregar os dados ou resetar o estado
    this.postagensExibidas = this.postagens.slice(inicio, fim);
  }
  
}
