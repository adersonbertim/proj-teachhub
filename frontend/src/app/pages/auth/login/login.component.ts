import { CommonModule } from '@angular/common';
import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../../core/services/auth/auth.service';
import { Router } from '@angular/router';


@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent {
  private authService = inject(AuthService);
  private router = inject(Router);

  loginData = {
    email: '',
    senha: ''
  };

  errorMessage: string = '';

  onSubmit(){
    this.authService.login(this.loginData).subscribe({
      next: (response) => {
        console.log('login realizado', response);
        //redirect to ia
        this.router.navigate(['/home'])
      },
      error: (err) => {
        this.errorMessage = 'Email invalido';
        console.error(err);
      }
    })
  }
  

}
