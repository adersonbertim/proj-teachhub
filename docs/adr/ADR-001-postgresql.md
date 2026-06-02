# ADR-001 - Utilização do PostgreSQL como Sistema Gerenciador de Banco de Dados

## Status

Aceito

## Contexto

O TeachHub é uma plataforma colaborativa voltada para professores, permitindo o compartilhamento de planos de aula, materiais didáticos, experiências e conhecimentos relacionados ao uso de tecnologias educacionais.

O sistema necessita armazenar informações estruturadas, incluindo usuários, perfis, postagens, avaliações, comentários, cursos e arquivos anexados. Dessa forma, tornou-se necessário selecionar um Sistema Gerenciador de Banco de Dados (SGBD) relacional que oferecesse confiabilidade, desempenho e capacidade de crescimento.

## Alternativas Consideradas

* MySQL
* PostgreSQL

## Decisão

Foi decidido utilizar o PostgreSQL como sistema gerenciador de banco de dados do TeachHub.

## Justificativa

A escolha do PostgreSQL foi motivada por sua robustez, conformidade com os padrões SQL, suporte a consultas complexas e capacidade de escalabilidade para aplicações que demandam crescimento contínuo.

Além disso, o PostgreSQL possui excelente integração com o ecossistema Spring Boot através do Spring Data JPA, facilitando o desenvolvimento e a manutenção da aplicação.

## Consequências Positivas

* Software livre e amplamente adotado no mercado.
* Excelente integração com Spring Boot.
* Suporte avançado a transações e integridade dos dados.
* Alto desempenho em aplicações relacionais.
* Facilidade para evolução do modelo de dados.

## Consequências Negativas

* Curva de aprendizado ligeiramente superior a outras alternativas.
* Necessidade de conhecimento específico para administração avançada do banco de dados.
* Pode exigir ajustes de configuração conforme o crescimento da aplicação.
