# OCG — Oscar com os Gurizes

Bolão do Oscar entre amigos, como API. Cada um palpita nos indicados de cada categoria; quando sai o resultado oficial, o ranking mostra quem acertou mais.

**Stack:** Java 21 · Spring Boot 3 · PostgreSQL · Docker

## Rodando em 2 comandos

```bash
docker compose up -d     # sobe o PostgreSQL
./mvnw spring-boot:run   # sobe a API em localhost:8080
```

Pré-requisitos: Java 21 e Docker.

## A ideia

- Existem **categorias** (Melhor Filme, Melhor Ator...) e **indicações** — um filme ou pessoa concorrendo em uma categoria num ano.
- Cada participante dá **um palpite por categoria**. Não vale mudar depois nem votar duas vezes — a API bloqueia.
- Quando o vencedor oficial é definido, um `GET /ranking` conta quantos palpites cada um acertou e ordena a galera.

## Rotas principais

```
POST   /users                        cria participante
POST   /users/login                  login
POST   /categories                   cria categoria
PATCH  /categories/{id}/winner       define o vencedor oficial
POST   /movies                       cadastra filme
POST   /votes                        registra palpite
GET    /votes/user/{userId}          palpites de alguém
GET    /ranking                      quem tá ganhando o bolão
```

Exemplo de palpite:

```bash
curl -X POST localhost:8080/votes \
  -H 'Content-Type: application/json' \
  -d '{"userId": "...", "nominationId": "..."}'
```

## O que eu pratiquei aqui

- Arquitetura em camadas (controller → service → repository) com DTOs
- Regra de negócio de verdade: um palpite por usuário/categoria
- Tratamento de erros centralizado com `@ControllerAdvice` e exceções de domínio
- Ranking calculado com agregação em JPQL direto no banco
- Containerização do PostgreSQL com Docker Compose

---

*Projeto de estudo. Sugestões e reviews são bem-vindos.*
