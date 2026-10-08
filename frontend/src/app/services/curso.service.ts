import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ApiResponse, CursoDTO, ResultadoYoutubeDTO } from './model.service';

@Injectable({
  providedIn: 'root'
})
export class CursoService {

  private readonly baseUrl = 'http://localhost:8080/curso';

  constructor(private http: HttpClient) {}

  listarTodos(): Observable<ApiResponse<CursoDTO[]>> {
    return this.http.get<ApiResponse<CursoDTO[]>>(`${this.baseUrl}/listar-todos`);
  }

  buscarPorId(id: number): Observable<ApiResponse<CursoDTO>> {
    return this.http.get<ApiResponse<CursoDTO>>(`${this.baseUrl}/${id}`);
  }

  listarParaGerenciar(): Observable<ApiResponse<CursoDTO[]>> {
    return this.http.get<ApiResponse<CursoDTO[]>>(`${this.baseUrl}/admin/todos`);
  }

  pesquisarNoYoutube(termo: string): Observable<ApiResponse<ResultadoYoutubeDTO[]>> {
    return this.http.get<ApiResponse<ResultadoYoutubeDTO[]>>(`${this.baseUrl}/admin/youtube`, { params: { termo } });
  }

  adicionar(resultado: ResultadoYoutubeDTO, categoria: string): Observable<ApiResponse<CursoDTO>> {
    return this.http.post<ApiResponse<CursoDTO>>(`${this.baseUrl}/admin`, { ...resultado, categoria });
  }

  adicionarPorLink(link: string, categoria: string): Observable<ApiResponse<CursoDTO>> {
    return this.http.post<ApiResponse<CursoDTO>>(`${this.baseUrl}/admin/link`, { link, categoria });
  }

  alterarAtivo(id: number, valor: boolean): Observable<ApiResponse<CursoDTO>> {
    return this.http.put<ApiResponse<CursoDTO>>(`${this.baseUrl}/admin/${id}/ativo`, null, { params: { valor } });
  }

  remover(id: number): Observable<ApiResponse<string>> {
    return this.http.delete<ApiResponse<string>>(`${this.baseUrl}/${id}`);
  }
}
