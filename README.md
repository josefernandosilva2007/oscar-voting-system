# 🏆 Oscar Voting System API

API REST desenvolvida para gerenciar um sistema de votação para o Oscar. O objetivo é permitir que usuários cadastrem filmes, categorias e realizem votos computados em tempo real.

> 🚧 **Status:** Em desenvolvimento

## 🛠️ Tecnologias Utilizadas

* **Java 21** (LTS)
* **Spring Boot 3**
* **PostgreSQL** (via Docker)
* **Docker Compose**
* **Lombok**
* **Maven**

## ⚙️ Como rodar o projeto

### Pré-requisitos

* Java 21 instalado
* Docker e Docker Compose instalados
* Git

### Passo a passo

1.  **Clone este repositório:**
    ```bash
    git clone [https://github.com/josefernandosilva2007/oscar-voting-system.git](https://github.com/josefernandosilva2007/oscar-voting-system.git)
    ```

2.  **Acesse a pasta do projeto:**
    ```bash
    cd oscar-voting-system
    ```

3.  **Suba o banco de dados via Docker:**
    ```bash
    docker compose up -d
    ```

4.  **Inicie a aplicação:**
    ```bash
    ./mvnw spring-boot:run
    ```

5.  A API estará rodando em: `http://localhost:8080`

## 📡 Endpoints Principais

Você pode testar utilizando **Insomnia** ou **Postman**.

### Usuários
* `POST /users` - Cadastrar novo usuário
* `GET /users` - Listar usuários

### Categorias
* `POST /categories` - Cadastrar nova categoria (ex: "Melhor Filme")
* `GET /categories` - Listar categorias

## 📝 Roadmap do Projeto

- [x] **Infraestrutura:** Configuração do Docker, PostgreSQL e conexão JPA.
- [x] **Recursos Básicos:** CRUD de Usuários e Categorias.
- [ ] **Filmes:** Cadastro de filmes vinculados a categorias.
- [ ] **Votos:** Lógica de votação (Regra: um voto por usuário/categoria).
- [ ] **Segurança:** Autenticação e Autorização com Spring Security.
- [ ] **Ranking:** Endpoint para contagem de votos e ranking final.

---

Desenvolvido como projeto de estudo prático focando em **Engenharia de Software** e boas práticas de mercado.