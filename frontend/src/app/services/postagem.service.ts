import { Injectable } from "@angular/core";
import { BehaviorSubject, Observable } from "rxjs";
import { ApiResponse, PostagemDTO } from "./model.service";
import { HttpClient } from '@angular/common/http';

@Injectable({
    providedIn: 'root'
})

export class PostagemService{
    private postagens : PostagemDTO [] =[];
// O BehaviorSubject avisa a todas as telas quando uma nova postagem surge
    private postagensSubject = new BehaviorSubject<PostagemDTO[]>([]);
    postagens$ = this.postagensSubject.asObservable();
    constructor(private http: HttpClient){}

        criarPostagem(dto: any){
            return this.http.post('http://localhost:8080/feed/criar', dto);
        }
        
        adicionarPostagens(novaPostagem: PostagemDTO){
            this.postagens = [novaPostagem, ...this.postagens];
            this.postagensSubject.next(this.postagens);
            console.log('Postagem salva', novaPostagem);
        }

        getPostagemById(id: number): Observable<ApiResponse<PostagemDTO>> {
            return this.http.get<ApiResponse<PostagemDTO>>(`http://localhost:8080/feed/postagens/${id}`);
        }
        
        listarFeed() {
        return this.http.get<any>('http://localhost:8080/feed/listar-feed');
        }

        deletarPostagem(id: number): Observable<ApiResponse<any>> {
            return this.http.delete<ApiResponse<any>>(`http://localhost:8080/feed/postagens/${id}`);
        }

}