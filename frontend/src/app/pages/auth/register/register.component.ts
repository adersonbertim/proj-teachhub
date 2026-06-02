import { CommonModule } from '@angular/common';
import { Component, inject } from '@angular/core';
import { FormsModule, Validators } from '@angular/forms';
import { AuthService } from '../../../core/services/auth/auth.service';

import { Router, RouterLink } from '@angular/router';
import { MaterialModule } from '../../../material-module';

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
  
//[Validators.required, Validators.minLength(6)],
  registerData = {
    nome: '',
    email: '',
    senha: '', 
    confirmarSenha: '',
    role: 'professor',
  }

  errorMessage: string = '';

  onRegister(){
    this.authService.register(this.registerData).subscribe({
      next: () => {
        alert('Cadastro realizado! Agora faça o login');
        this.router.navigate(["/login"]);
      },
      error: (err: { error: { message: string; }; }) => {
        this.errorMessage = err.error?.message || 'Erro ao realizar o cadastro!';
      }
    })
  }

  
}
