import { Injectable } from '@angular/core';

export interface PostagemDTO{
isFavorita: boolean;
isPlanoAula: boolean;
nota : boolean;
likes: number;
dislikes: number;
materia: string;
conteudo: any;
dataCriacao: string|number|Date;
    idPostagem: number;
    titulo: string;
    descricao: string;
    categoria: string;
    autor: string;
}

