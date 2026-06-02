# ADR-009 - Controle de Acesso Baseado em Papéis (RBAC)

## Status

Aceito

## Contexto

O TeachHub possui diferentes tipos de usuários que executam funções distintas dentro da plataforma.

Enquanto professores utilizam os recursos de compartilhamento de conteúdo, interação e colaboração, administradores necessitam de permissões adicionais para gerenciamento e manutenção do sistema.

Dessa forma, tornou-se necessário definir um mecanismo de controle de acesso que permita restringir funcionalidades de acordo com o perfil do usuário.

## Alternativas Consideradas

* Controle de acesso sem diferenciação de perfis.
* Controle de acesso baseado em papéis (RBAC).

## Decisão

Foi decidido utilizar o modelo de Controle de Acesso Baseado em Papéis (Role-Based Access Control - RBAC).

## Justificativa

O modelo RBAC permite associar permissões a papéis específicos, simplificando o gerenciamento de acessos e aumentando a segurança da aplicação.

No TeachHub foram definidos inicialmente os seguintes papéis:

* PROFESSOR
* ADMINISTRADOR

As permissões serão concedidas conforme as responsabilidades de cada perfil dentro da plataforma.

## Consequências Positivas

* Maior segurança na aplicação.
* Facilidade de gerenciamento de permissões.
* Escalabilidade para inclusão de novos papéis no futuro.
* Integração simplificada com Spring Security.

## Consequências Negativas

* Necessidade de manutenção das regras de autorização.
* Possível aumento da complexidade conforme novos papéis e permissões forem adicionados.
* Exige testes adicionais para garantir a correta restrição de acesso.
