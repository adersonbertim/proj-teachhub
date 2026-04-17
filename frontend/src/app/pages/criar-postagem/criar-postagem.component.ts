import { Component, ElementRef, ViewChild } from '@angular/core';
import { PostagemService } from '../../services/postagem.service';
import { Router } from '@angular/router';
import { PostagemDTO } from '../../services/model.service';
import { CommonModule, Location } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MaterialModule } from '../../material-module';

@Component({
  selector: 'app-criar-postagem',
  imports: [CommonModule, FormsModule, MaterialModule],
  templateUrl: './criar-postagem.component.html',
  styleUrl: './criar-postagem.component.scss',
})
export class CriarPostagemComponent {

  @ViewChild('editor') editor!: ElementRef;
  autor: string = '';
  titulo: string = '';
  materia: string = '';
  isPlanoAula: boolean = false;
  descricao: string = '';
  categoria: string = '';

  constructor(
    private postagemService: PostagemService,
    private router: Router,
    private location: Location,
  ) {}

  execCommand(command: string) {
    document.execCommand(command, false, '');
  }
  promptLink() {
    const url = prompt('Insira a URL do link: ');
    if (url) document.execCommand('createLink', false, url);
  }
  triggerImage() {
    const img = prompt('Insira a url da imagem: ');
    if (img) document.execCommand('insertImage', false, img);
  }

  //metodo para enviar arquivos do compudator
  onFileSelected(event: any) {
    const file = event.target.files[0];
    if (file) {
      const reader = new FileReader();

      reader.onload = (e: any) => {
        const base64Image = e.target.result;
        // Inserimos a imagem no exato ponto do cursor
        this.insertImageAtCursor(base64Image);
      };

      reader.readAsDataURL(file); // Converte imagem para string Base64
    }
  }

  private insertImageAtCursor(src: string) {
    this.editor.nativeElement.focus();
    document.execCommand('insertImage', false, src);
  }

  criarPostagem() {
    const conteudo = this.editor.nativeElement.innerHTML;
    const novaPostagem: PostagemDTO = {
      autor: this.autor,
      titulo: this.titulo,
      descricao: this.descricao,
      categoria: this.categoria,
      idPostagem: 0,
      isPlanoAula: false,
      materia: '',
      resumo: '',
      dataCriacao: '',
      isFavorita: false,
      nota: false,
      likes: 0,
      dislikes: 0,
    };
    this.postagemService.adicionarPostagens(novaPostagem);
    this.router.navigate(['/postagens']);
  }
    voltar() {
    if(window.history.length < 1){
      this.location.back()
    }else{
      this.router.navigate(['/home']);
    }
  }
}
