import { Injectable } from "@angular/core";
import { BehaviorSubject } from "rxjs";
import { PostagemDTO } from "./model.service";


@Injectable({
    providedIn: 'root'
})

export class PostagemService{
    private postagens : PostagemDTO [] =[];
// O BehaviorSubject avisa a todas as telas quando uma nova postagem surge
    private postagensSubject = new BehaviorSubject<PostagemDTO[]>([]);
    postagens$ = this.postagensSubject.asObservable();

    constructor(){}

        adicionarPostagens(novaPostagem: PostagemDTO){
            this.postagens = [novaPostagem, ...this.postagens];
            this.postagensSubject.next(this.postagens);
            console.log('Postagem salva', novaPostagem);
        }

        getPostagem(){
            return this.postagens;
        }
    


}