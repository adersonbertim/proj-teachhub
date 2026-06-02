# ADR-003 - Utilização do Spring Boot para o Desenvolvimento do Backend

## Status

Aceito

## Contexto

O TeachHub necessita de uma API responsável pelo gerenciamento de usuários, autenticação, postagens, avaliações, arquivos e demais recursos da plataforma.

Era necessário escolher uma tecnologia backend que oferecesse produtividade, segurança, estabilidade e suporte à arquitetura RESTful adotada pelo projeto.

## Alternativas Consideradas

* Java com Spring Boot
* Python
* Rust

## Decisão

Foi decidido utilizar Java com Spring Boot para o desenvolvimento do backend da aplicação.

## Justificativa

A decisão foi baseada na familiaridade da equipe com a linguagem Java e no amplo ecossistema disponibilizado pelo Spring Boot.

O framework fornece recursos nativos para desenvolvimento de APIs REST, autenticação, integração com banco de dados, validação de dados e gerenciamento de dependências, reduzindo o esforço de implementação e manutenção.

Além disso, a tecnologia possui ampla adoção no mercado e grande disponibilidade de documentação e suporte da comunidade.

## Consequências Positivas

* Alta produtividade no desenvolvimento.
* Forte integração com PostgreSQL através do Spring Data JPA.
* Recursos nativos para construção de APIs REST.
* Grande comunidade e ampla documentação.
* Facilidade de manutenção e evolução do sistema.

## Consequências Negativas

* Maior consumo de memória quando comparado a alternativas mais leves.
* Tempo de inicialização superior a algumas tecnologias.
* Curva de aprendizado do ecossistema Spring para novos desenvolvedores.
