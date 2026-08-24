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

  constructor(
    private perfilService: PerfilService,
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

  salvar() {
    if (!this.perfil) return;
    this.salvando = true;

    const dto = {
      descricao: this.perfil.descricao,
      imagemPerfil: this.perfil.imagemPerfil,
      visibilidade: this.perfil.visibilidade,
      redesSociais: this.perfil.redesSociais
    };

    this.perfilService.atualizarPerfil(dto).subscribe({
      next: (response) => {
        this.salvando = false;
        this.router.navigate(['/perfil', response.data.id]);
      },
      error: (err) => {
        console.error('Erro ao salvar perfil:', err);
        this.salvando = false;
      }
    });
  }

  cancelar() {
    if (this.perfil) {
      this.router.navigate(['/perfil', this.perfil.id]);
    } else {
      this.router.navigate(['/home']);
    }
  }
}