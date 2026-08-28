import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MaterialModule } from '../../../material-module';
import { PerfilService } from '../../../services/perfil.service';
import { Perfil } from '../../../services/model.service';

@Component({
  selector: 'app-perfil-config',
  imports: [CommonModule, FormsModule, MaterialModule],
  templateUrl: './perfil-config.component.html',
  styleUrl: './perfil-config.component.scss'
})
export class PerfilConfigComponent implements OnInit {
  perfil?: Perfil;
  carregando = true;
  salvando = false;
  salvo = false;

  enviandoFoto = false;
  fotoPreview?: string;

  constructor(
    private perfilService: PerfilService,
    private router: Router
  ) {}

  ngOnInit() {
    this.perfilService.buscarMeuPerfil().subscribe({
      next: (response) => {
        this.perfil = response.data;
        // garante que o objeto existe para o ngModel não quebrar nos inputs de redes sociais
        if (!this.perfil.redesSociais) {
          this.perfil.redesSociais = {};
        }
        this.carregando = false;
      },
      error: (err) => {
        console.error('Erro ao carregar seu perfil:', err);
        this.carregando = false;
      }
    });
  }

  selecionarFoto(event: Event) {
    const input = event.target as HTMLInputElement;
    const arquivo = input.files?.[0];
    if (!arquivo) return;

    if (!arquivo.type.startsWith('image/')) {
      alert('Selecione um arquivo de imagem (JPG, PNG ou WEBP).');
      input.value = '';
      return;
    }

    const tamanhoMaximoMB = 5;
    if (arquivo.size > tamanhoMaximoMB * 1024 * 1024) {
      alert(`A imagem precisa ter no máximo ${tamanhoMaximoMB}MB.`);
      input.value = '';
      return;
    }

    // Preview instantâneo, antes mesmo do upload terminar
    this.fotoPreview = URL.createObjectURL(arquivo);
    this.enviandoFoto = true;

    this.perfilService.enviarFotoPerfil(arquivo).subscribe({
      next: (response) => {
        if (this.perfil) {
          this.perfil.imagemPerfil = response.data.imagemPerfil;
        }
        this.enviandoFoto = false;
      },
      error: (err) => {
        console.error('Erro ao enviar foto:', err);
        this.enviandoFoto = false;
        this.fotoPreview = undefined;
      }
    });

    input.value = ''; // permite escolher o mesmo arquivo de novo depois, se quiser
  }

  salvar() {
    if (!this.perfil) return;
    this.salvando = true;
    this.salvo = false;

    const dto = {
      descricao: this.perfil.descricao,
      visibilidade: this.perfil.visibilidade,
      redesSociais: this.perfil.redesSociais
    };

    this.perfilService.atualizarPerfil(dto).subscribe({
      next: (response) => {
        this.salvando = false;
        this.salvo = true;
        this.router.navigate(['/perfil']);
      },
      error: (err) => {
        console.error('Erro ao salvar perfil:', err);
        this.salvando = false;
      }
    });
  }

  // Volta pra visão de fora do seu próprio perfil (usa /perfil, sem precisar do id)
  cancelar() {
    this.router.navigate(['/perfil']);
  }
}