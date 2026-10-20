import { NumberInput } from '@angular/cdk/coercion';
import { Injectable } from '@angular/core';

export interface UsuarioDTO {
  id: number;
  email: string;
  nome: string;
  dataCadastro: Date | string | number;
  areaEnsino: string;
  score: number;
  imagemPerfil?: string;
}
export interface PostagemDTO {
  id: number;
  idPostagem: number;
  titulo: string;
  descricao: string;
  categoria: string;
  autor: string;
  tags: string[];
  visibilidade?: boolean;
  isFavorita?: boolean | string;
  conteudo: boolean;
  tarefa: boolean;
  nota: boolean;
  likes: number;
  dislikes: number;
  materia: string;
  resumo: string;
  dataCriacao: string | number | Date;
  usuarioDTO?: UsuarioDTO;
}

export interface PostagemRequestDTO {
  titulo: string;
  texto: string;
  categoria: string;
  tags: string;
}

export interface Pagina<T> {
  content: T[];
  totalElements: number;
  totalPages: number;
  number: number;
  size: number;
}

export interface CursoDTO {
  idCurso: number;
  titulo: string;
  plataforma: string;
  categoria: string;
  link: string;
  descricao?: string;
  imagem?: string;
  canal?: string;
  idExterno?: string;
  ativo?: boolean;
}

export interface ResultadoYoutubeDTO {
  idExterno: string;
  tipo: 'PLAYLIST' | 'VIDEO';
  titulo: string;
  descricao: string;
  imagem: string;
  canal: string;
  jaAdicionado: boolean;
}

export interface FavoritoDTO{
    idFavorito: number;
    idPostagem: number;
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

export interface ComentarioDTO{
    id: number;
    titulo: string;
    texto: string;
    data: string;
    nomeUsuario: string;
    fotoUsuario?: string;
    idComentarioPai?: number;
    qtdLikes: number;
    curtidoPeloUsuario: boolean;
    respostas: ComentarioDTO[];
    mostrandoRespostas?: boolean;
    respondendo?: boolean;
    novaResposta?: string;
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

export interface LikeStatusDTO {
  qtdLikes: number;
  curtidoPeloUsuario: boolean;
}
