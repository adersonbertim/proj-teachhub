# ADR-007 - Utilização da Arquitetura MVC

## Status

Aceito

## Contexto

O TeachHub necessita de uma organização arquitetural que facilite a separação de responsabilidades, a manutenção do código e a evolução das funcionalidades ao longo do tempo.

A aplicação possui uma interface desenvolvida em Angular e um backend desenvolvido em Spring Boot, sendo necessário definir uma estrutura que permita a organização adequada das regras de negócio, controle das requisições e apresentação dos dados.

## Alternativas Consideradas

* Arquitetura MVC (Model-View-Controller)
* Clean Architecture
* Arquitetura Hexagonal

## Decisão

Foi decidido utilizar a arquitetura MVC como padrão de organização da aplicação.

## Justificativa

A arquitetura MVC é amplamente utilizada em aplicações desenvolvidas com Spring Boot e permite uma clara separação entre os componentes responsáveis pela representação dos dados, controle das requisições e exibição das informações.

No contexto do TeachHub, o Angular atua como camada de apresentação (View), enquanto o Spring Boot é responsável pelo processamento das requisições (Controller) e pelas regras de negócio e persistência dos dados (Model).

Além disso, a familiaridade da equipe com esse padrão arquitetural contribui para um desenvolvimento mais produtivo e uma manutenção simplificada.

## Consequências Positivas

* Separação clara de responsabilidades.
* Facilidade de manutenção do código.
* Maior organização da aplicação.
* Facilidade para inclusão de novas funcionalidades.
* Compatibilidade natural com o ecossistema Spring Boot.

## Consequências Negativas

* Pode gerar aumento da quantidade de classes conforme a aplicação cresce.
* Dependência de uma boa organização dos componentes para evitar acoplamento excessivo.
* Menor flexibilidade arquitetural quando comparada a modelos mais modernos, como Clean Architecture.
