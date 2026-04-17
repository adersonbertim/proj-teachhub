import { Injectable } from '@angular/core';

export interface UsuarioDTO {
  idUsuario: number;
  email: string;
  nome: string;
  dataCadastro: Date | string | number;
  areaEnsino: string;
  score: number;
}
export interface PostagemDTO {
  idPostagem: number;
  titulo: string;
  descricao: string;
  categoria: string;
  autor: string;
  tag?: string;
  visibilidade?: string;
  isFavorita?: boolean;
  isPlanoAula: boolean;
  nota: boolean;
  likes: number;
  dislikes: number;
  materia: string;
  resumo: string;
  dataCriacao: string | number | Date;
}

export interface CursoDTO {
  idCurso: number;
  titulo: string;
  plataforma: string;
  categoria: string;
  link: string;
}

export interface FavoritoDTO{
    idFavorito: number;
    dataFavorito: Date | string | number;
    nomeUsuario: string;
    tituloPostagem: string;
}


export interface Ia{
    idIa: number;
    pergunta: string;
    resposta: string;
    data : Date | string | number;
    userIa: UsuarioDTO;
}

export interface Comentario{
    idComentario: number;
    titulo: string;
    texto: string;
    avaliacao: number;
    likes: number;
    dislikes: number;
    dataComentario: Date | string | number;
    usuario: UsuarioDTO;
    postagem: PostagemDTO;
}