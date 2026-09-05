import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { IaService } from '../../services/ia.service';
import { MaterialModule } from '../../material-module';

@Component({
  selector: 'app-ia-chat',
  imports: [CommonModule, FormsModule, MaterialModule],
  templateUrl: './ia-chat.component.html',
  styleUrl: './ia-chat.component.scss'
})
export class IaChatComponent implements OnInit {

  constructor(private router: Router, private iaService: IaService) {}
  userInput: string = '';


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
        this.message.push({
          text: "Problema de conexão com o servidor.",
          type: 'ia',
        });
        this.scrollToBottom(); 
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