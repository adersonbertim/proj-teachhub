import { CommonModule } from '@angular/common';
import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../../core/services/auth/auth.service';

import { Router, RouterLink } from '@angular/router';
import { MaterialModule } from '../../../material-module';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [CommonModule, FormsModule, MaterialModule, RouterLink],
  templateUrl: './register.component.html',
  styleUrl: './register.component.scss'
})
export class RegisterComponent {
  private authService = inject(AuthService);
  private router = inject(Router);

  registerData = {
    nome: '',
    email: '',
    senha: '',
    confirmarSenha: ''
  }

  errorMessage: string = '';
  carregando = false;

  registrarNovaConta(){
    if (this.registerData.senha !== this.registerData.confirmarSenha) {
      this.errorMessage = 'As senhas não são iguais.';
      return;
    }

    this.errorMessage = '';
    this.carregando = true;

    const { nome, email, senha } = this.registerData;

    this.authService.register({ nome, email, senha }).subscribe({
      next: () => {
        this.carregando = false;
        Swal.fire({
          title: "Cadastro realizado com sucesso!",
          text: "Agora é só entrar com seu e-mail e senha.",
          icon: "success"
        }).then(() => this.router.navigate(["/login"]));
      },
      error: (err) => {
        this.carregando = false;
        if (err.status === 0) {
          this.errorMessage = 'Não foi possível conectar ao servidor.';
        } else {
          this.errorMessage = err.error?.message || 'Erro ao realizar o cadastro!';
        }
      }
    })
  }
}