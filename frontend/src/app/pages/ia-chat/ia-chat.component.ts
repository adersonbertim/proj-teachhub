import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { IaService } from '../../services/ia.service';
import { MaterialModule } from '../../material-module';
import { DomSanitizer, SafeHtml } from '@angular/platform-browser';
import { marked } from 'marked';
import Swal from 'sweetalert2';
import { IaService } from '../../services/ia.service';
import { IaService } from '../../services/ia.service';

@Component({
  selector: 'app-ia-chat',
  imports: [CommonModule, FormsModule, MaterialModule],
  templateUrl: './ia-chat.component.html',
  styleUrl: './ia-chat.component.scss'
})
export class IaChatComponent implements OnInit {

  constructor(private router: Router, private iaService: IaService, private sanitizer: DomSanitizer) {}
  constructor(private router: Router, private iaService: IaService) {}
  constructor(private router: Router, private iaService: IaService) {}
  userInput: string = '';

  renderizarMarkdown(texto: string): SafeHtml {
    const html = marked.parse(texto, { async: false }) as string;
    return this.sanitizer.bypassSecurityTrustHtml(html);
  }

  voltar() {
    return this.router.navigate(['/home']);
  }

  message = [{
     text: 'Olá! Eu sou o assistente do TeachHub. Como posso te ajudar com seus estudos hoje?', type: 'ia'
  }];

  ngOnInit() {
    this.iaService.getHistorico().subscribe({
      next: (res) => {
        if (res.data && res.data.length > 0) {
          const historicoOrdenado = [...res.data].reverse();
          this.message = historicoOrdenado.map((log: any) => [
            { text: log.pergunta, type: 'user' },
            { text: log.resposta, type: 'ia' }
          ]).flat();
        }
      },
      error: (err) => console.error('Erro ao carregar histórico', err)
    });
  }

  sendMessage() {
  if (this.userInput.trim()) {
    const question = this.userInput;

    this.message.push({
      text: question,
      type: 'user',
    });
    this.scrollToBottom();
  ngOnInit() {
    this.iaService.getHistorico().subscribe({
      next: (res) => {
        if (res.data) {
          // mapeia o histórico para o formato esperado pelo componente de chat
          this.message = res.data.map((log: any) => [
            { text: log.pergunta, type: 'user' },
            { text: log.resposta, type: 'ia' }
          ]).flat();
        }
      },
      error: (err) => console.error('Erro ao carregar histórico', err)
    });
  }
  ngOnInit() {
    this.iaService.getHistorico().subscribe({
      next: (res) => {
        if (res.data) {
          // mapeia o histórico para o formato esperado pelo componente de chat
          this.message = res.data.map((log: any) => [
            { text: log.pergunta, type: 'user' },
            { text: log.resposta, type: 'ia' }
          ]).flat();
        }
      },
      error: (err) => console.error('Erro ao carregar histórico', err)
    });
  }

  // sendMessage agora faz logica de adicionar a pergunta do usuário imediatamente e depois chama o backend para obter a resposta
  sendMessage() {
  if (this.userInput.trim()) {
    const question = this.userInput;

    // adiciona a pergunta do usuário na tela
    this.message.push({
      text: question,
      type: 'user',
    });

  // sendMessage agora faz logica de adicionar a pergunta do usuário imediatamente e depois chama o backend para obter a resposta
  sendMessage() {
  if (this.userInput.trim()) {
    const question = this.userInput;

    // adiciona a pergunta do usuário na tela
    this.message.push({
      text: question,
      type: 'user',
    });

    this.userInput = '';
    this.userInput = '';

    this.iaService.perguntar(question).subscribe({
      next: (res) => {

        this.message.push({
          text: res.data,
          type: 'ia',
        });
        this.scrollToBottom();
      },
      error: (err) => {
    // faz chamada para o Backend
    this.iaService.perguntar(question).subscribe({
      next: (res) => {

        this.message.push({
          text: res.data,
          type: 'ia',
        });
      },
      error: (err) => {
    // faz chamada para o Backend
    this.iaService.perguntar(question).subscribe({
      next: (res) => {

        this.message.push({
          text: res.data,
          type: 'ia',
        });
      },
      error: (err) => {
        this.message.push({
          text: "Problema de conexão com o servidor.",
          type: 'ia',
        });
        this.scrollToBottom();
      }
    });
      }
    });
      }
    });
  }
}

  scrollToBottom() {
    setTimeout(() => {
      const chatContainer = document.querySelector('.messages-container');
      if (chatContainer) {
        chatContainer.scrollTop = chatContainer.scrollHeight;
      }
    }, 100);
  }
}
}

 limparConversa() {
    Swal.fire({
      title: "Tem certeza?",
      text: "Você não poderá desfazer esta ação!",
      icon: "warning",
      showCancelButton: true,
      confirmButtonColor: "#1976d2",
      confirmButtonText: "Sim, excluir!",
      cancelButtonText: "Cancelar"
    }).then((result) => {
      if (result.isConfirmed) {
        this.iaService.limparHistorico().subscribe({
          next: () => {
            this.message = [{
              text: 'Olá! Eu sou o assistente do TeachHub. Como posso te ajudar com seus estudos hoje?',
              type: 'ia'
            }];
            Swal.fire("Excluído!", "Seu histórico foi excluído.", "success");
          },
          error: (err: any) => {console.error('Erro ao limpar histórico', err);  Swal.fire('Erro', 'Não foi possível excluir o histórico. Tente novamente.', 'error');}
        });
      }
    });
}

  }
