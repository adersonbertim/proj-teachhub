import { AfterViewInit, Component, ElementRef, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { CommonModule, Location } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MaterialModule } from '../../../material-module';
import { PostagemService } from '../../../services/postagem.service';
import { PostagemRequestDTO } from '../../../services/model.service';
import Quill from 'quill';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-criar-postagem',
  imports: [CommonModule, FormsModule, MaterialModule],
  templateUrl: './criar-postagem.component.html',
  styleUrl: './criar-postagem.component.scss',
})
export class CriarPostagemComponent implements AfterViewInit {

@ViewChild('editor') editor!: ElementRef<HTMLDivElement>;
quill!: Quill;
titulo: string = '';
conteudo: boolean = true;
tarefa: boolean = false;
categoria: string = '';
tags: string = '';
erroTags = '';
erroEnvio = '';
salvando = false;
enviandoImagem = false;
imagemSelecionada: HTMLImageElement | null = null;
molduraImagem = { top: 0, left: 0, width: 0, height: 0 };
tamanhosImagem = [
  { rotulo: 'Pequena', largura: '25%' },
  { rotulo: 'Média', largura: '50%' },
  { rotulo: 'Grande', largura: '75%' },
  { rotulo: 'Original', largura: '' },
];  
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
    }
  );
    this.quill.root.addEventListener('click', (evento) => {
    const alvo = evento.target as HTMLElement;
    if (alvo.tagName === 'IMG') {
      this.imagemSelecionada = alvo as HTMLImageElement;
      this.posicionarMoldura();
    } else {
      this.imagemSelecionada = null;
    }
      });
      this.quill.root.addEventListener('scroll', () => this.imagemSelecionada = null);
      this.quill.on('text-change', (_delta, _antigo, origem) => {
        if (origem === 'user') this.imagemSelecionada = null;
      });
      this.quill.on('selection-change', (range) => {
        if (!range) this.imagemSelecionada = null;
      });
  }

  redimensionarImagem(largura: string) {
    if (!this.imagemSelecionada) return;
    const blot = Quill.find(this.imagemSelecionada);
    if (!blot || blot instanceof Quill) return;

    const indice = this.quill.getIndex(blot);
    this.quill.formatText(indice, 1, 'width', largura || false, 'api');
    this.posicionarMoldura();
  }

  larguraAtual(): string {
    return this.imagemSelecionada?.getAttribute('width') || '';
  }

  private posicionarMoldura() {
  if (!this.imagemSelecionada) return;
  const imagem = this.imagemSelecionada.getBoundingClientRect();
  const editor = this.editor.nativeElement.parentElement!.getBoundingClientRect();
  this.molduraImagem = {
    top: imagem.top - editor.top,
    left: imagem.left - editor.left,
    width: imagem.width,
    height: imagem.height,
  };
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
  this.imagemInput ??= Object.assign(document.createElement('input'), { type: 'file', accept: 'image/jpeg,image/png,image/webp' });
  this.imagemInput.onchange = () => {
    const arquivo = this.imagemInput.files?.[0];
    this.imagemInput.value = '';
    if (!arquivo) return;

    if (!['image/jpeg', 'image/png', 'image/webp'].includes(arquivo.type)) {
      this.erroEnvio = 'Envie uma imagem JPG, PNG ou WEBP.';
      return;
    }
    if (arquivo.size > 5 * 1024 * 1024) {
      this.erroEnvio = 'A imagem precisa ter no máximo 5MB.';
      return;
    }

    const range = this.quill.getSelection(true);
    this.erroEnvio = '';
    this.enviandoImagem = true;

    this.postagemService.enviarImagem(arquivo).subscribe({
      next: (res) => {
        this.enviandoImagem = false;
        this.quill.insertEmbed(range.index, 'image', res.data, 'user');
        this.quill.setSelection(range.index + 1, 0, 'silent');
      },
      error: (err) => {
        this.enviandoImagem = false;
        this.erroEnvio = err.error?.message || 'Não foi possível enviar a imagem.';
      }
    });
  };
  this.imagemInput.click();
}

  get tagsDigitadas(): string[] {
    const nomes = this.tags.split('#').map(tag => tag.trim().toLowerCase()).filter(Boolean);
    return [...new Set(nomes)];
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
  if (!valido) {
    Swal.fire('Confira as tags', this.erroTags, 'warning');
  }
  return valido;
}

  criarPostagem() {
  if (this.salvando) return;
 const textoVazio = this.quill.getText().trim() === '' && !this.quill.root.querySelector('img');
  let faltando = '';
  if (!this.titulo.trim()) faltando = 'Informe o título da postagem.';
  else if (!this.categoria) faltando = 'Escolha a categoria.';
  else if (textoVazio) faltando = 'Escreva o texto da postagem.';
  if (faltando) {
    this.erroEnvio = faltando;
    Swal.fire('Falta pouco', faltando, 'warning');
    return;
  }

  if (!this.validarTags()) return;

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
      Swal.fire('Erro', this.erroEnvio, 'error');
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
