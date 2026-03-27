import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';
import { Router, NavigationEnd } from '@angular/router';
import { filter } from 'rxjs/operators';

@Component({
  selector: 'app-support-chat',
  imports: [CommonModule],
  templateUrl: './support-chat.component.html',
  styleUrls: ['./support-chat.component.scss']
})
export class SupportChatComponent {
  @Input() isDarkMode = false;
  isOpen = false;
  isIAPage = false;

  constructor(private router: Router) {
    // Escuta as mudanças de rota
    this.router.events.pipe(
      filter(event => event instanceof NavigationEnd)
    ).subscribe((event: any) => {
      // Se a URL contiver '/ia', escondemos o suporte flutuante
      this.isIAPage = event.url.includes('/ia');
    });
  }

  toggleChat() {
    this.isOpen = !this.isOpen;
  }
}