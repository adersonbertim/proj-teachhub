import { Injectable } from '@angular/core';

export interface UsuarioDTO {
  id: number;
  email: string;
  nome: string;
  dataCadastro: Date | string | number;
  areaEnsino: string;
  score: number;
}
export interface PostagemDTO {
  texto: string;
  idPostagem: number;
  titulo: string;
  descricao: string;
  categoria: string;
  autor: string;
  tag?: string;
  visibilidade?: boolean;
  isFavorita?: boolean;
  isPlanoAula: boolean;
  nota: boolean;
  likes: number;
  dislikes: number;
  materia: string;
  resumo: string;
  dataCriacao: string | number | Date;
  usuarioDTO?: UsuarioDTO;
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

export interface ApiResponse<T> {
    status: string;
    data: T;
    message: string;
}

export interface Perfil{
  id: number;
  nome: string;
  descricao: string;
  imagemPerfil?: string;

  visibilidade: 'PUBLICO' | 'PRIVADO';

  redesSociais?: {
    instagram?: string;
    facebook?: string;
    twitter?: string;
    linkedin?: string;
  };

  postagens?: PostagemDTO[];

  souDono?: boolean;
}