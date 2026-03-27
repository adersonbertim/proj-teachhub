import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-ia-chat',
  imports: [CommonModule, FormsModule],
  templateUrl: './ia-chat.component.html',
  styleUrl: './ia-chat.component.scss'
})
export class IaChatComponent {

  constructor(private router: Router) {}
  userInput: string = '';


  voltar() {
    return this.router.navigate(['/home']);
  }

  message = [{
     text: 'Olá! Eu sou o assistente do TeachHub. Como posso te ajudar com seus estudos hoje?', type: 'ai' 
  }];

  sendMessage(){
    if(this.userInput.trim()){
      this.message.push({
        text: this.userInput,
        type: 'user',
      })

      const question = this.userInput;
      this.userInput = '';

      setTimeout(() => {
        this.message.push({
          text: `Você perguntou sobre "${question}" e estou processando uma resposta, aguarde um instante...`,
          type: 'ia',
        });
      }, 1000);
    }
  }

  
}
