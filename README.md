# 📚 TeachHub

TeachHub é uma plataforma desenvolvida para facilitar a colaboração e o compartilhamento de recursos entre educadores. O sistema oferece ferramentas para criação, gerenciamento e distribuição de conteúdos educacionais, além de promover a comunicação e o trabalho em equipe dentro de comunidades de ensino.

---

## 🎯 Objetivo

O TeachHub tem como objetivo central otimizar o processo de ensino-aprendizagem, permitindo que educadores criem, compartilhem e colaborem em conteúdos de forma eficiente, promovendo um ambiente integrado e interativo.

---

## 🚀 Funcionalidades

### 👤 Autenticação de usuários

* Criação de conta
* Login

### 🔍 Busca de conteúdo

* Pesquisa de postagens
* Pesquisa de cursos

### 📝 Postagens

* Criação de conteúdos educacionais
* Compartilhamento de materiais

### 🤝 Colaboração

* Interação entre usuários

### 🤖 Integração com IA

* Consulta inteligente para apoio educacional

### 📊 Gestão de usuários

* Controle de acesso baseado em perfis

---

## 🛠️ Tecnologias Utilizadas

### 🔹 Frontend

* Angular
* Angular Material (componentes visuais e estilização)
* TypeScript

### 🔹 Backend

* Java
* Spring Boot
* Arquitetura REST (API RESTful)

### 🔹 Banco de Dados

* PostgreSQL

---

## 🏗️ Arquitetura do Projeto

O backend segue o padrão de arquitetura em camadas:

* **Controller** → Responsável pelas requisições HTTP
* **Service** → Contém a lógica de negócio
* **Repository** → Comunicação com o banco de dados
* **Entity** → Representação das tabelas
* **DTO (Data Transfer Object)** → Transferência de dados entre camadas

---

## ⚙️ Como Executar o Projeto

### 📌 Pré-requisitos

* Java 21+
* Node.js
* Angular CLI
* PostgreSQL

---

### 🔧 Backend

```bash
# Clone o repositório
git clone <https://github.com/adersonbertim/proj-teachhub.git>

# Acesse a pasta do backend
cd backend

# Execute a aplicação
./mvnw spring-boot:run
```

---

### 💻 Frontend

```bash
# Acesse a pasta do frontend
cd frontend

# Instale as dependências
npm install

# Execute o projeto
ng serve
```

---

## 🗄️ Banco de Dados

* SGBD: PostgreSQL
* Configurar as credenciais no arquivo `application.properties` ou `application.yml` que estão em `application.properties-template`

---

## 📌 Estrutura do Projeto

```
backend/
    ├──  src/main/java/
        ├── config/
        ├── controller/
        ├── core/
        ├── model/
        ├── service/

frontend/
 ├── src/
    ├── app/
        ├── core/
        ├── layouts/
        ├── pages/
        ├── services/
    ├── assets/
 
```

---

## 🔗 API (Exemplo de Endpoints)

```
GET    /postagens  
POST   /criar-postagens  
GET    /cursos  
POST   /auth/login  
POST   /auth/register  
```

---

## 👨‍💻 Autor

DEV - Aderson Bertim
DEV - Régis da Silva Soares
Guilher Paim Zinelli
Thilber Falcão Ribas
Abilio Goulart Barbosa

---

## 📄 Licença

Este projeto está sob a licença MIT.

## 🤖 Uso de Inteligência Artificial

Ferramentas de inteligência artificial foram utilizadas como apoio no desenvolvimento deste projeto, auxiliando na revisão de código, organização de ideias, ajustes pontuais no codigo e melhorias na documentação. Todas as decisões de implementação e lógica de negócio foram definidas pelos desenvolvedores.