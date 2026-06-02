# ADR-005 - Utilização de Login Social com Google OAuth2

## Status

Aceito

## Contexto

O TeachHub busca oferecer uma experiência de autenticação simples e acessível para seus usuários.

Embora o sistema possua autenticação tradicional utilizando usuário e senha, também foi identificada a necessidade de reduzir barreiras de entrada e facilitar o processo de cadastro e login.

## Alternativas Consideradas

* Apenas autenticação por usuário e senha.
* Autenticação por usuário e senha combinada com Google OAuth2.

## Decisão

Foi decidido implementar autenticação social utilizando Google OAuth2 em conjunto com o método tradicional de login.

## Justificativa

O login social permite que usuários utilizem suas contas Google já existentes para acessar a plataforma, reduzindo a necessidade de criação e gerenciamento de novas credenciais.

A utilização do OAuth2 delega o processo de autenticação ao Google, aumentando a confiabilidade do processo de login e melhorando a experiência do usuário.

Além disso, o Spring Security fornece integração simplificada com provedores OAuth2.

## Consequências Positivas

* Processo de login mais rápido.
* Redução da quantidade de senhas gerenciadas pelos usuários.
* Melhor experiência de uso.
* Menor atrito durante o cadastro.
* Integração nativa com Spring Security OAuth2.

## Consequências Negativas

* Dependência de um provedor externo de autenticação.
* Possíveis indisponibilidades do serviço externo impactam o login social.
* Necessidade de gerenciamento das credenciais OAuth fornecidas pelo Google.
