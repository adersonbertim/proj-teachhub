import { CommonModule } from '@angular/common';
import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../../core/services/auth/auth.service';
import { Router, RouterLink } from '@angular/router';
import { MaterialModule } from '../../../material-module';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule, MaterialModule, RouterLink],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss',
})
export class LoginComponent {
  private authService = inject(AuthService);
  private router = inject(Router);

  loginData = {
    email: '',
    senha: '',
  };

  errorMessage: string = '';

  login() {
    this.authService.login(this.loginData).subscribe({
      next: (response) => {
        
        console.log('login realizado', response);
        this.router.navigate(['/home']);
      },
      error: (err) => {
        this.errorMessage = 'Email ou senha inválidos';
        console.error(err);
      },
    });
  }

  loginGoogle() {
    console.log('fazer login com google');
  }
}
