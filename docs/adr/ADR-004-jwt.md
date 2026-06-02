# ADR-004 - Utilização de JWT para Autenticação

## Status

Aceito

## Contexto

O TeachHub necessita de um mecanismo de autenticação seguro para controlar o acesso às funcionalidades da plataforma, garantindo que apenas usuários autenticados possam realizar operações como criar postagens, avaliar conteúdos, alterar informações de perfil e acessar recursos restritos.

Além disso, a aplicação foi projetada utilizando uma arquitetura baseada em API REST, onde o backend e o frontend operam de forma desacoplada.

## Alternativas Consideradas

* Autenticação baseada em sessão (Session-Based Authentication)
* JSON Web Token (JWT)

## Decisão

Foi decidido utilizar JSON Web Token (JWT) como mecanismo principal de autenticação da aplicação.

## Justificativa

O JWT permite a implementação de autenticação stateless, eliminando a necessidade de armazenamento de sessões no servidor.

Essa abordagem é adequada para arquiteturas RESTful, facilita a escalabilidade da aplicação e possibilita a integração entre diferentes clientes, como aplicações web e futuras aplicações móveis.

Além disso, o Spring Security oferece suporte nativo à implementação de autenticação baseada em JWT.

## Consequências Positivas

* Não requer armazenamento de sessão no servidor.
* Melhor escalabilidade da aplicação.
* Integração simplificada entre frontend e backend.
* Compatibilidade com aplicações distribuídas.
* Suporte nativo através do Spring Security.

## Consequências Negativas

* Revogação de tokens exige mecanismos adicionais.
* Necessidade de gerenciamento do tempo de expiração dos tokens.
