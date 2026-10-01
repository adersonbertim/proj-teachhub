import { CommonModule, isPlatformBrowser } from '@angular/common';
import { AfterViewInit, Component, ElementRef, inject, NgZone, PLATFORM_ID, ViewChild } from '@angular/core';
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
export class LoginComponent implements AfterViewInit {
  private authService = inject(AuthService);
  private router = inject(Router);
  private zone = inject(NgZone);
  private platformId = inject(PLATFORM_ID);

  @ViewChild('botaoGoogle') botaoGoogle!: ElementRef<HTMLDivElement>;


  private readonly GOOGLE_CLIENT_ID = '457772450392-o25t5dqki80bc3fhji9dcc8k8ofj4d5b.apps.googleusercontent.com';

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
  
   ngAfterViewInit() {
    if (!isPlatformBrowser(this.platformId)) return;

    const script = document.createElement('script');
    script.src = 'https://accounts.google.com/gsi/client';
    script.async = true;
    script.onload = () => this.renderizarBotaoGoogle();
    document.body.appendChild(script);
  }

  private renderizarBotaoGoogle() {
    const google = (window as any).google;

    google.accounts.id.initialize({
      client_id: this.GOOGLE_CLIENT_ID,
      callback: (resposta: any) => this.zone.run(() => this.loginGoogle(resposta.credential))
    });

    google.accounts.id.renderButton(this.botaoGoogle.nativeElement, {
      theme: 'outline',
      size: 'large',
      text: 'continue_with',
      shape: 'pill',
      locale: 'pt-BR',
      width: 320
    });
  }

 loginGoogle(credential: string) {
    this.authService.loginGoogle(credential).subscribe({
      next: () => this.router.navigate(['/home']),
      error: (err) => {
        this.errorMessage = 'Não foi possível entrar com o Google';
        console.error(err);
      },
    });
  }
}
