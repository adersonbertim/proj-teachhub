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


  buscarPerfil(id: number): Observable<ApiResponse<Perfil>> {
    return this.http.get<ApiResponse<Perfil>>(`${this.baseUrl}/${id}`);
  }

  buscarMeuPerfil(): Observable<ApiResponse<Perfil>> {
    return this.http.get<ApiResponse<Perfil>>(`${this.baseUrl}/me`);
  }

  atualizarPerfil(dto: Partial<Perfil>): Observable<ApiResponse<Perfil>> {
    return this.http.put<ApiResponse<Perfil>>(`${this.baseUrl}/me`, dto);
  }
}