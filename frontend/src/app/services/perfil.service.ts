import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ApiResponse, Perfil } from './model.service';

@Injectable({
  providedIn: 'root'
})
export class PerfilService {

  private readonly baseUrl = 'http://localhost:8080/perfil';

  constructor(private http: HttpClient) {}

  // Perfil de visitante (ou o próprio, se o id for o do usuário logado — o backend
  // já devolve souDono: true nesse caso)
  buscarPerfil(id: number): Observable<ApiResponse<Perfil>> {
    return this.http.get<ApiResponse<Perfil>>(`${this.baseUrl}/${id}`);
  }

  // Perfil completo do usuário autenticado (usado na tela de configurações)
  buscarMeuPerfil(): Observable<ApiResponse<Perfil>> {
    return this.http.get<ApiResponse<Perfil>>(`${this.baseUrl}/me`);
  }

  // Atualização dos dados de texto (descrição, visibilidade, redes sociais).
  // A foto NÃO vai mais aqui — veja enviarFotoPerfil.
  atualizarPerfil(dto: Partial<Perfil>): Observable<ApiResponse<Perfil>> {
    return this.http.put<ApiResponse<Perfil>>(`${this.baseUrl}/me`, dto);
  }

  // Upload da foto de perfil — envia como multipart, não JSON.
  // Não define Content-Type manualmente: o browser monta o header
  // multipart/form-data com o boundary certo sozinho.
  enviarFotoPerfil(arquivo: File): Observable<ApiResponse<Perfil>> {
    const formData = new FormData();
    formData.append('arquivo', arquivo);
    return this.http.post<ApiResponse<Perfil>>(`${this.baseUrl}/me/foto`, formData);
  }
}