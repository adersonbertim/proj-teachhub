import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import Swal from 'sweetalert2';
import { MaterialModule } from '../../../material-module';
import { CursoService } from '../../../services/curso.service';
import { CursoDTO, ResultadoYoutubeDTO } from '../../../services/model.service';
import { CATEGORIAS_CURSO, ehPlaylist } from '../categorias';

@Component({
  selector: 'app-cursos-gerenciar',
  imports: [CommonModule, FormsModule, MaterialModule, RouterLink],
  templateUrl: './cursos-gerenciar.component.html',
  styleUrl: './cursos-gerenciar.component.scss'
})
export class CursosGerenciarComponent implements OnInit {
  categorias = CATEGORIAS_CURSO;
  ehPlaylist = ehPlaylist;

  termo = '';
  resultados: ResultadoYoutubeDTO[] = [];
  categoriaDoResultado: Record<string, string> = {};
  pesquisando = false;
  pesquisou = false;

  link = '';
  categoriaDoLink = '';
  adicionandoLink = false;

  cadastrados: CursoDTO[] = [];
  carregandoCadastrados = true;

  constructor(private cursoService: CursoService) {}

  ngOnInit() {
    this.carregarCadastrados();
  }

  get totalOfertados(): number {
    return this.cadastrados.filter(c => c.ativo).length;
  }

  pesquisar() {
    const termo = this.termo.trim();
    if (!termo) return;

    this.pesquisando = true;
    this.cursoService.pesquisarNoYoutube(termo).subscribe({
      next: (res) => {
        this.resultados = res.data || [];
        this.pesquisando = false;
        this.pesquisou = true;
      },
      error: (err) => {
        console.error('Erro ao pesquisar no YouTube:', err);
        this.pesquisando = false;
        this.avisarErro(err, 'Não foi possível pesquisar no YouTube.');
      }
    });
  }

  adicionar(resultado: ResultadoYoutubeDTO) {
    const categoria = this.categoriaDoResultado[resultado.idExterno];
    if (!categoria) {
      Swal.fire('Escolha uma categoria', 'Selecione a categoria antes de adicionar o curso.', 'info');
      return;
    }

    this.cursoService.adicionar(resultado, categoria).subscribe({
      next: (res) => {
        resultado.jaAdicionado = true;
        this.cadastrados = [res.data, ...this.cadastrados];
        this.avisarSucesso('Curso adicionado');
      },
      error: (err) => this.avisarErro(err, 'Não foi possível adicionar o curso.')
    });
  }

  adicionarPorLink() {
    if (!this.link.trim() || !this.categoriaDoLink) return;

    this.adicionandoLink = true;
    this.cursoService.adicionarPorLink(this.link.trim(), this.categoriaDoLink).subscribe({
      next: (res) => {
        this.cadastrados = [res.data, ...this.cadastrados];
        this.link = '';
        this.adicionandoLink = false;
        this.avisarSucesso('Curso adicionado');
      },
      error: (err) => {
        this.adicionandoLink = false;
        this.avisarErro(err, 'Não foi possível adicionar esse link.');
      }
    });
  }

  alterarAtivo(curso: CursoDTO, valor: boolean) {
    this.cursoService.alterarAtivo(curso.idCurso, valor).subscribe({
      next: (res) => curso.ativo = res.data.ativo,
      error: (err) => {
        curso.ativo = !valor;
        this.avisarErro(err, 'Não foi possível alterar o curso.');
      }
    });
  }

  remover(curso: CursoDTO) {
    Swal.fire({
      title: 'Remover curso?',
      text: `"${curso.titulo}" deixará de aparecer para os professores.`,
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#1976d2',
      cancelButtonColor: '#ccc',
      confirmButtonText: 'Sim, remover',
      cancelButtonText: 'Cancelar'
    }).then((result) => {
      if (!result.isConfirmed) return;

      this.cursoService.remover(curso.idCurso).subscribe({
        next: () => {
          this.cadastrados = this.cadastrados.filter(c => c.idCurso !== curso.idCurso);
          const resultado = this.resultados.find(r => r.idExterno === curso.idExterno);
          if (resultado) resultado.jaAdicionado = false;
        },
        error: (err) => this.avisarErro(err, 'Não foi possível remover o curso.')
      });
    });
  }

  linkDoResultado(resultado: ResultadoYoutubeDTO): string {
    return resultado.tipo === 'PLAYLIST'
      ? `https://www.youtube.com/playlist?list=${resultado.idExterno}`
      : `https://www.youtube.com/watch?v=${resultado.idExterno}`;
  }

  private carregarCadastrados() {
    this.cursoService.listarParaGerenciar().subscribe({
      next: (res) => {
        this.cadastrados = res.data || [];
        this.carregandoCadastrados = false;
      },
      error: (err) => {
        console.error('Erro ao carregar cursos cadastrados:', err);
        this.carregandoCadastrados = false;
      }
    });
  }

  private avisarSucesso(mensagem: string) {
    Swal.fire({ toast: true, position: 'bottom-end', icon: 'success', title: mensagem, showConfirmButton: false, timer: 2000 });
  }

  private avisarErro(err: any, padrao: string) {
    Swal.fire('Erro', err?.error?.message || padrao, 'error');
  }
}
