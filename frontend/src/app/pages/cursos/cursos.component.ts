import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { MaterialModule } from '../../material-module';
import { CursoService } from '../../services/curso.service';
import { CursoDTO } from '../../services/model.service';
import { AuthService } from '../../core/services/auth/auth.service';
import { CATEGORIAS_CURSO, ehPlaylist, iconeDaCategoria } from './categorias';

@Component({
  selector: 'app-cursos',
  imports: [CommonModule, FormsModule, MaterialModule, RouterLink],
  templateUrl: './cursos.component.html',
  styleUrl: './cursos.component.scss'
})
export class CursosComponent implements OnInit {
  categorias = CATEGORIAS_CURSO;
  iconeDaCategoria = iconeDaCategoria;
  ehPlaylist = ehPlaylist;

  cursos: CursoDTO[] = [];
  termo = '';
  categoriaSelecionada: string | null = null;
  carregando = true;
  erro = false;
  isAdmin = false;

  constructor(private cursoService: CursoService, private authService: AuthService) {}

  ngOnInit() {
    this.isAdmin = this.authService.isAdmin();

    this.cursoService.listarTodos().subscribe({
      next: (res) => {
        this.cursos = res.data || [];
        this.carregando = false;
      },
      error: (err) => {
        console.error('Erro ao carregar cursos:', err);
        this.erro = true;
        this.carregando = false;
      }
    });
  }

  get cursosFiltrados(): CursoDTO[] {
    const termo = this.termo.trim().toLowerCase();

    return this.cursos.filter(curso =>
      (!this.categoriaSelecionada || curso.categoria === this.categoriaSelecionada) &&
      (!termo || curso.titulo.toLowerCase().includes(termo) || curso.canal?.toLowerCase().includes(termo))
    );
  }

  quantidadeNaCategoria(nome: string): number {
    return this.cursos.filter(c => c.categoria === nome).length;
  }

  selecionarCategoria(nome: string | null) {
    this.categoriaSelecionada = this.categoriaSelecionada === nome ? null : nome;
  }

  limparFiltros() {
    this.termo = '';
    this.categoriaSelecionada = null;
  }
}
