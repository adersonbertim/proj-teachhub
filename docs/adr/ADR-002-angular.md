# ADR-002 - Utilização do Angular como Framework Frontend

## Status

Aceito

## Contexto

O TeachHub necessita de uma interface web moderna, responsiva e capaz de fornecer uma experiência de navegação fluida para seus usuários.

A aplicação possui funcionalidades como autenticação, gerenciamento de perfil, criação de postagens, avaliações e consumo de conteúdos educacionais, exigindo uma arquitetura organizada e de fácil manutenção.

## Alternativas Consideradas

* Angular
* React
* Flutter

## Decisão

Foi decidido utilizar o Angular como framework para o desenvolvimento do frontend da aplicação.

## Justificativa

A equipe possui familiaridade com Angular, reduzindo o tempo de aprendizado e aumentando a produtividade durante o desenvolvimento.

Além disso, o Angular fornece uma arquitetura baseada em componentes e módulos, incentivando a organização do código, reutilização de funcionalidades e manutenção simplificada do sistema.

O framework também possui integração eficiente com APIs REST, requisito fundamental para a arquitetura adotada pelo TeachHub.

## Consequências Positivas

* Estrutura modular e organizada.
* Facilidade de manutenção e escalabilidade.
* Forte integração com APIs REST.
* Grande quantidade de recursos nativos.
* Padronização do desenvolvimento frontend.

## Consequências Negativas

* Curva de aprendizado superior a algumas alternativas.
* Maior quantidade de código inicial em comparação com outras bibliotecas.
* Aplicações simples podem demandar estrutura considerada excessiva.
