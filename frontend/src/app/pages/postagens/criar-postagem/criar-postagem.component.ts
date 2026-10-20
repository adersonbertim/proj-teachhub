import { Component, ElementRef, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { CommonModule, Location } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MaterialModule } from '../../../material-module';
import { PostagemService } from '../../../services/postagem.service';
import { PostagemRequestDTO } from '../../../services/model.service';


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
  conteudo: boolean = false;
  tarefa: boolean = false;
  texto: string = '';
  categoria: string = '';
  tags: string = '';
  erroTags = '';
  erroEnvio = '';
  salvando = false;
  visibilidade: boolean = true;
  isFavorita: boolean = false;

  constructor(
    private postagemService: PostagemService,
    private router: Router,
    private location: Location,
  ) {}

  executar(command: string) {
    document.execCommand(command, false, '');
  }
  link() {
    const url = prompt('Insira a URL do link: ');
    if (url) document.execCommand('createLink', false, url);
  }
  imagemUrl() {
    const img = prompt('Insira a url da imagem: ');
    if (img) document.execCommand('insertImage', false, img);
  }

  //metodo para enviar arquivos do compudator
  selecionarArquivo(event: any) {
    const file = event.target.files[0];
    if (file) {
      const reader = new FileReader();

      reader.onload = (e: any) => {
        const base64Image = e.target.result;
        // Inserimos a imagem no exato ponto do cursor
        this.inserirImagem(base64Image);
      };

      reader.readAsDataURL(file); // Converte imagem para string Base64
    }
  }

  private inserirImagem(src: string) {
    this.editor.nativeElement.focus();
    document.execCommand('insertImage', false, src);
  }

  private validarTags(): boolean {
    const entrada = this.tags.trim();
    const nomes = entrada.split('#')
      .map(tag => tag.trim())
      .filter(Boolean);
    const tagsUnicas = new Set(nomes.map(tag => tag.toLowerCase()));

    const valido = entrada.startsWith('#')
      && tagsUnicas.size >= 1
      && tagsUnicas.size <= 10;

    this.erroTags = valido ? '' : 'Informe de 1 a 10 tags usando #.';
    return valido;
  }

  criarPostagem() {
    if (this.salvando || !this.validarTags()) return;

    const dto: PostagemRequestDTO = {
      titulo: this.titulo,
      texto: this.editor.nativeElement.innerText,
      categoria: this.categoria,
      tags: this.tags,
    };

    this.salvando = true;
    this.erroEnvio = '';
    this.postagemService.criarPostagem(dto).subscribe({
      next: (res) => {
        this.postagemService.adicionarPostagens(res.data);
        this.router.navigate(['/postagens']);
      },
      error: (err) => {
        this.salvando = false;
        this.erroEnvio = err.error?.message || 'Não foi possível criar a postagem.';
      },
    });
  }
    voltar() {
    if(window.history.length < 1){
      this.location.back()
    }else{
      this.router.navigate(['/home']);
    }
  }
}
