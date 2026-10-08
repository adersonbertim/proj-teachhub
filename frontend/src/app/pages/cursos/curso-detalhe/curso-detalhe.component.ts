import { Component, OnInit } from '@angular/core';
import { CommonModule, Location } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';
import { MaterialModule } from '../../../material-module';
import { CursoService } from '../../../services/curso.service';
import { CursoDTO } from '../../../services/model.service';
import { ehPlaylist } from '../categorias';

@Component({
  selector: 'app-curso-detalhe',
  imports: [CommonModule, MaterialModule],
  templateUrl: './curso-detalhe.component.html',
  styleUrl: './curso-detalhe.component.scss'
})
export class CursoDetalheComponent implements OnInit {
  curso?: CursoDTO;
  urlPlayer?: SafeResourceUrl;
  carregando = true;
  erro = false;
  ehPlaylist = ehPlaylist;

  constructor(
    private route: ActivatedRoute,
    private location: Location,
    private cursoService: CursoService,
    private sanitizer: DomSanitizer
  ) {}

  ngOnInit() {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    if (isNaN(id)) {
      this.erro = true;
      this.carregando = false;
      return;
    }

    this.cursoService.buscarPorId(id).subscribe({
      next: (res) => {
        this.curso = res.data;
        this.urlPlayer = this.montarUrlPlayer(this.curso);
        this.carregando = false;
      },
      error: (err) => {
        console.error('Erro ao carregar curso:', err);
        this.erro = true;
        this.carregando = false;
      }
    });
  }

  voltar() {
    this.location.back();
  }

  private montarUrlPlayer(curso: CursoDTO): SafeResourceUrl | undefined {
    if (curso.plataforma !== 'YouTube' || !curso.idExterno) return undefined;

    const id = encodeURIComponent(curso.idExterno);
    const url = ehPlaylist(curso.link)
      ? `https://www.youtube-nocookie.com/embed/videoseries?list=${id}`
      : `https://www.youtube-nocookie.com/embed/${id}`;

    return this.sanitizer.bypassSecurityTrustResourceUrl(url);
  }
}
