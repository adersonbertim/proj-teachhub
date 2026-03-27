import { Injectable } from '@angular/core';

export interface PostagemDTO{
isPlanoAula: any;
materia: string;
conteudo: any;
dataCriacao: string|number|Date;
    idPostagem: number;
    titulo: string;
    descricao: string;
    categoria: string;
    autor: string;
}