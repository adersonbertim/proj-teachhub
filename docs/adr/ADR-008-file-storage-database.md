# ADR-008 - Armazenamento de Arquivos Diretamente no Banco de Dados

## Status

Aceito

## Contexto

O TeachHub permite que os usuários compartilhem materiais educacionais, incluindo documentos PDF e imagens relacionados a planos de aula e conteúdos pedagógicos.

Dessa forma, tornou-se necessário definir uma estratégia para armazenamento dos arquivos enviados pelos usuários.

## Alternativas Consideradas

* Armazenamento dos arquivos diretamente no banco de dados.
* Armazenamento em sistema de arquivos do servidor.
* Armazenamento em serviços externos de objetos (S3, MinIO, Azure Blob Storage).

## Decisão

Foi decidido armazenar os arquivos diretamente no banco de dados PostgreSQL.

## Justificativa

Considerando o escopo inicial do projeto e a simplicidade desejada para a infraestrutura, o armazenamento dos arquivos diretamente no banco de dados permite centralizar as informações da aplicação em uma única solução de persistência.

Essa abordagem reduz a necessidade de configuração e gerenciamento de serviços adicionais de armazenamento durante as fases iniciais de desenvolvimento.

## Consequências Positivas

* Centralização dos dados da aplicação.
* Simplificação da infraestrutura.
* Facilidade de backup e restauração.
* Controle de acesso unificado aos dados e arquivos.

## Consequências Negativas

* Crescimento acelerado do banco de dados.
* Possível redução de desempenho em operações envolvendo arquivos grandes.
* Maior consumo de espaço de armazenamento.
* Dificuldade de escalabilidade em comparação com soluções especializadas de armazenamento de objetos.
