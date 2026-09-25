import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ApiResponse, FavoritoDTO } from './model.service';

@Injectable({
  providedIn: 'root'
})
export class FavoritoService {

  private readonly baseUrl = 'http://localhost:8080/favoritos';

  constructor(private http: HttpClient) {}

  toggleFavorito(postagemId: number): Observable<ApiResponse<boolean>> {
    return this.http.post<ApiResponse<boolean>>(`${this.baseUrl}/${postagemId}`, {});
  }

  listarMeus(): Observable<ApiResponse<FavoritoDTO[]>> {
    return this.http.get<ApiResponse<FavoritoDTO[]>>(`${this.baseUrl}/meus`);
  }
}