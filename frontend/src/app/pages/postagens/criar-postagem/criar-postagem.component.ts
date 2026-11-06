import { AfterViewInit, Component, ElementRef, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { CommonModule, Location } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MaterialModule } from '../../../material-module';
import { PostagemService } from '../../../services/postagem.service';
import { PostagemRequestDTO } from '../../../services/model.service';
import Quill from 'quill';

@Component({
  selector: 'app-criar-postagem',
  imports: [CommonModule, FormsModule, MaterialModule],
  templateUrl: './criar-postagem.component.html',
  styleUrl: './criar-postagem.component.scss',
})
export class CriarPostagemComponent implements AfterViewInit {

  @ViewChild('editor') editor!: ElementRef<HTMLDivElement>;
  quill!: Quill;
  autor: string = '';
  titulo: string = '';
  materia: string = '';
  conteudo: boolean = true;
  tarefa: boolean = false;
  texto: string = '';
  categoria: string = '';
  tags: string = '';
  erroTags = '';
  erroEnvio = '';
  salvando = false;
  visibilidade: boolean = true;
  isFavorita: boolean = false;
  imagemInput!: HTMLInputElement;

  constructor(
    private postagemService: PostagemService,
    private router: Router,
    private location: Location,
  ) {}

  ngAfterViewInit() {
    this.quill = new Quill(this.editor.nativeElement, {
      theme: 'snow',
      placeholder: 'Comece a escrever seu conteúdo aqui...',
      modules: {
        toolbar: {
          container: [['bold', 'italic', 'underline'], [{ align: [] }], ['link', 'image'], [{ list: 'ordered' }, { list: 'bullet' }]],
          handlers: { link: () => this.inserirLink(), image: () => this.selecionarImagem() },
        },
      },
    });
  }

  inserirLink() {
    const range = this.quill.getSelection(true);
    if (!range) return;
    const url = prompt('Insira a URL do link:');
    if (!url) return;
    const endereco = /^(https?:|mailto:)/i.test(url) ? url : `https://${url}`;
    this.quill.setSelection(range);
    if (range.length) this.quill.format('link', endereco, 'user');
    else {
      const texto = prompt('Texto do link:', endereco) || endereco;
      this.quill.insertText(range.index, texto, { link: endereco }, 'user');
    }
  }

  private selecionarImagem() {
    this.imagemInput ??= Object.assign(document.createElement('input'), { type: 'file', accept: 'image/*' });
    this.imagemInput.onchange = () => {
      const arquivo = this.imagemInput.files?.[0];
      if (!arquivo || !arquivo.type.startsWith('image/')) return;
      const leitor = new FileReader();
      leitor.onload = () => {
        const range = this.quill.getSelection(true);
        if (!range) return;
        this.quill.insertEmbed(range.index, 'image', leitor.result, 'user');
        this.quill.setSelection(range.index + 1, 0, 'silent');
      };
      leitor.readAsDataURL(arquivo);
      this.imagemInput.value = '';
    };
    this.imagemInput.click();
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
      texto: this.quill.root.innerHTML,
      categoria: this.categoria,
      tags: this.tags,
      conteudo: this.conteudo,
      tarefa: this.tarefa,
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
