# ADR-006 - Utilização de Arquitetura RESTful para Comunicação entre Cliente e Servidor

## Status

Aceito

## Contexto

O TeachHub possui uma arquitetura separada entre frontend e backend, sendo necessário definir um padrão de comunicação para troca de informações entre as camadas da aplicação.

A plataforma necessita disponibilizar recursos relacionados a usuários, postagens, avaliações, autenticação e demais funcionalidades através de interfaces acessíveis e padronizadas.

## Alternativas Consideradas

* REST
* GraphQL
* SOAP

## Decisão

Foi decidido utilizar uma arquitetura baseada em serviços RESTful para comunicação entre o frontend Angular e o backend Spring Boot.

## Justificativa

A arquitetura REST é amplamente adotada no desenvolvimento de aplicações web modernas, possuindo forte compatibilidade com Angular e suporte nativo através das anotações disponibilizadas pelo Spring Boot, como @GetMapping, @PostMapping, @PutMapping e @DeleteMapping.

Além disso, REST simplifica a implementação de APIs, facilita a manutenção e reduz a complexidade da comunicação entre os componentes do sistema.

## Consequências Positivas

* Arquitetura amplamente conhecida e documentada.
* Facilidade de integração com aplicações web.
* Implementação simplificada utilizando Spring Boot.
* Baixa complexidade operacional.
* Facilidade de manutenção e evolução da API.

## Consequências Negativas

* Possibilidade de transferência excessiva de dados em alguns cenários.
* Necessidade de múltiplas requisições para obtenção de recursos relacionados.
* Menor flexibilidade em comparação com soluções como GraphQL para consultas complexas.
