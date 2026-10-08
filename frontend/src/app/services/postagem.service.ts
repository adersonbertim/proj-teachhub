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
    `http://localhost:8080/likes/postagem/${id}`,
  );
}

estadoLikePostagem(id: number): Observable<ApiResponse<LikeStatusDTO>> {
  return this.http.post<ApiResponse<LikeStatusDTO>>(
    `http://localhost:8080/likes/postagem/${id}/toggle`,
    {},
  );
}

getFavoritoStatus(id: number): Observable<ApiResponse<boolean>> {
  return this.http.get<ApiResponse<boolean>>(
    `http://localhost:8080/favoritos/postagem/${id}/status`,
  );
}

estadoFavorito(id: number): Observable<ApiResponse<boolean>> {
  return this.http.post<ApiResponse<boolean>>(
    `http://localhost:8080/favoritos/${id}`,
    {},
  );
}

getComentarios(id: number): Observable<ApiResponse<ComentarioDTO[]>> {
  return this.http.get<ApiResponse<ComentarioDTO[]>>(
    `http://localhost:8080/comentarios/postagem/${id}`,
  );
}

enviarComentario(id: number, novoComentario: string): Observable<ApiResponse<ComentarioDTO>> {
  return this.http.post<ApiResponse<ComentarioDTO>>(
    `http://localhost:8080/comentarios/postagem/${id}`,
    { texto: novoComentario },
  );
}

// aqui o id é o do COMENTÁRIO pai
enviarResposta(idComentario: number, novaResposta: string): Observable<ApiResponse<ComentarioDTO>> {
  return this.http.post<ApiResponse<ComentarioDTO>>(
    `http://localhost:8080/comentarios/${idComentario}/resposta`,
    { texto: novaResposta },
  );
}

estadoLikeComentario(id: number): Observable<ApiResponse<ComentarioDTO>> {
  return this.http.post<ApiResponse<ComentarioDTO>>(
    `http://localhost:8080/comentarios/${id}/like/estado`,
    {},
  );
}
  getRelacionadas(id: number): Observable<ApiResponse<PostagemDTO[]>> {
    return this.http.get<ApiResponse<PostagemDTO[]>>(
      `http://localhost:8080/feed/postagens/${id}/relacionadas`,
    );
  }
}
