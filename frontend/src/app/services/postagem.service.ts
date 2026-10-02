import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import {
  ApiResponse,
  ComentarioDTO,
  LikeStatusDTO,
  PostagemDTO,
} from './model.service';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root',
})
export class PostagemService {
  private postagens: PostagemDTO[] = [];
  // O BehaviorSubject avisa a todas as telas quando uma nova postagem surge
  private postagensSubject = new BehaviorSubject<PostagemDTO[]>([]);
  postagens$ = this.postagensSubject.asObservable();
  constructor(private http: HttpClient) {}

  criarPostagem(dto: any) {
    return this.http.post('http://localhost:8080/feed/criar', dto);
  }

  adicionarPostagens(novaPostagem: PostagemDTO) {
    this.postagens = [novaPostagem, ...this.postagens];
    this.postagensSubject.next(this.postagens);
    console.log('Postagem salva', novaPostagem);
  }

  getPostagemById(id: number): Observable<ApiResponse<PostagemDTO>> {
    return this.http.get<ApiResponse<PostagemDTO>>(
      `http://localhost:8080/feed/postagens/${id}`,
    );
  }

  listarFeed() {
    return this.http.get<any>('http://localhost:8080/feed/listar-feed');
  }

  deletarPostagem(id: number): Observable<ApiResponse<PostagemDTO>> {
    return this.http.delete<ApiResponse<PostagemDTO>>(
      `http://localhost:8080/feed/postagens/${id}`,
    );
  }

  getLikeStatus(id: number): Observable<ApiResponse<LikeStatusDTO>> {
    return this.http.get<ApiResponse<LikeStatusDTO>>(
      `http://localhost:8080/feed/postagens/${id}/like`,
    );
  }
  estadoLikePostagem(id: number): Observable<ApiResponse<LikeStatusDTO>> {
    return this.http.post<ApiResponse<LikeStatusDTO>>(
      `http://localhost:8080/feed/postagens/${id}/like/estado`,
      {},
    );
  }
  getFavoritoStatus(
    id: number,
  ): Observable<ApiResponse<{ isFavorita: boolean }>> {
    return this.http.get<ApiResponse<{ isFavorita: boolean }>>(
      `http://localhost:8080/feed/postagens/${id}/favorito`,
    );
  }
  estadoFavorito(id: number): Observable<ApiResponse<{ isFavorita: boolean }>> {
    return this.http.post<ApiResponse<{ isFavorita: boolean }>>(
      `http://localhost:8080/feed/postagens/${id}/favorito/estado`,
      {},
    );
  }
  getComentarios(id: number): Observable<ApiResponse<ComentarioDTO[]>> {
    return this.http.get<ApiResponse<ComentarioDTO[]>>(
      `http://localhost:8080/feed/postagens/${id}/comentarios`,
    );
  }
  enviarComentario(
    id: number,
    novoComentario: string,
  ): Observable<ApiResponse<ComentarioDTO>> {
    return this.http.post<ApiResponse<ComentarioDTO>>(
      `http://localhost:8080/feed/postagens/${id}/comentarios`,
      { texto: novoComentario },
    );
  }
  enviarResposta(
    id: number,
    novaResposta: string,
  ): Observable<ApiResponse<ComentarioDTO>> {
    return this.http.post<ApiResponse<ComentarioDTO>>(
      `http://localhost:8080/feed/postagens/${id}/respostas`,
      { texto: novaResposta },
    );
  }
  estadoLikeComentario(id: number): Observable<ApiResponse<LikeStatusDTO>> {
    return this.http.post<ApiResponse<LikeStatusDTO>>(
      `http://localhost:8080/feed/comentarios/${id}/like/estado`,
      {},
    );
  }
  getRelacionadas(id: number): Observable<ApiResponse<PostagemDTO[]>> {
    return this.http.get<ApiResponse<PostagemDTO[]>>(
      `http://localhost:8080/feed/postagens/${id}/relacionadas`,
    );
  }
}
