import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MaterialModule } from '../../../material-module';
import { PerfilService } from '../../../services/perfil.service';
import { AuthService } from '../../../core/services/auth/auth.service';
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
    private authService: AuthService,
    private router: Router
  ) {}

  ngOnInit() {
    this.perfilService.buscarMeuPerfil().subscribe({
      next: (response) => {
        this.perfil = response.data;
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

    input.value = ''; 
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

  cancelar() {
    this.router.navigate(['/perfil']);
  }

  sair() {
    this.authService.logout();
    this.router.navigate(['/login']);
  }
}