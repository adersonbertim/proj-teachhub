import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class IaService {
  // mudar futuramente para o endereço do backend Java
  private apiUrl = 'http://localhost:8080/ia';
  
  constructor(private http: HttpClient) { }

  // envia a pergunta para o backend e retorna a resposta
  perguntar(pergunta: string): Observable<any> {
    const body = { pergunta: pergunta }; 
    return this.http.post(`${this.apiUrl}/perguntar`, body);
  }

  // pega o histórico de perguntas e respostas do banco de dados
  getHistorico(): Observable<any> {
    return this.http.get(`${this.apiUrl}/historico`);
  }

}