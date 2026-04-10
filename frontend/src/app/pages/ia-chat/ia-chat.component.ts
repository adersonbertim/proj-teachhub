import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { IaService } from '../../services/ia.service';

@Component({
  selector: 'app-ia-chat',
  imports: [CommonModule, FormsModule],
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
     text: 'Olá! Eu sou o assistente do TeachHub. Como posso te ajudar com seus estudos hoje?', type: 'ai' 
  }];

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

    this.userInput = ''; 

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
      }
    });
  }
}

  
}
