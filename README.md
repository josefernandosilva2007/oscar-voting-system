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
- Cada participante dá **um palpite por categoria**. A verificação considera usuário, categoria e ano da indicação; ainda não há proteção contra requisições concorrentes.
- Quando o vencedor oficial é definido, um `GET /ranking` conta quantos palpites cada um acertou e ordena a galera.

## Rotas principais

```
POST   /users                        cria participante
POST   /users/login                  login
POST   /categories                   cria categoria
PATCH  /categories/{id}/winner       define a indicação vencedora ({"nominationId":"..."})
POST   /people                       cadastra pessoa
GET    /people                       lista pessoas
POST   /movies                       cadastra filme
POST   /nominations                  cadastra indicação
GET    /nominations                  lista indicações
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

## Contratos do modelo

- `POST /users`: `username`, `email`, `password` (mínimo de 8 caracteres). A senha é armazenada com PBKDF2 e não é retornada.
- `POST /users/login`: `username`, `password`. Valida credenciais; ainda não cria sessão ou token.
- `POST /people`: `name`, `imageUrl` opcional.
- `POST /movies`: `name`, `synopsis` e `personId` opcionais.
- `POST /categories`: `name`.
- `POST /nominations`: `categoryId`, `movieId`, `personId`, `year`, `status`.
- `status` é um inteiro obrigatório conforme o diagrama; seus valores de negócio ainda não foram definidos.
- `isWinner` permanece nulo até a definição do resultado. Definir vencedor atualiza as indicações da mesma categoria e ano.
- O ranking soma acertos de todos os anos cadastrados.

A tabela de usuários usa o nome físico `app_user`, evitando a palavra reservada `user` do PostgreSQL.
Bancos existentes precisam de migração explícita de tabela, campos e dados; `ddl-auto=update` não renomeia estruturas nem converte senhas antigas. Faça backup antes de atualizar.

A API ainda não possui autorização, prazo de votação ou garantia de unicidade de votos sob concorrência. Esses pontos exigem trabalho adicional ao modelo documentado.

## Testes unitários

Com um JDK 21 configurado em `JAVA_HOME`, execute:

```bash
bash mvnw '-Dtest=ModelRefactorTests,*ServiceTest' test
```

Os 24 testes unitários usam JUnit 5 e Mockito, sem PostgreSQL, Redis ou Docker.
Cobrem hash e proteção da senha na serialização, login válido e inválido, usuário excluído,
atualização de imagem, cadastro de filmes e indicações, referências inexistentes,
seleção do vencedor por categoria/ano e validação e apresentação dos votos.
Para executar os testes unitários e a inicialização do contexto com H2:

```bash
bash mvnw test
```

O teste `contextLoads` usa o perfil `test` e H2 em memória, sem depender de PostgreSQL ou Docker.
Esse comando executa 25 testes. A inicialização valida a criação dos repositórios.

### Lombok e JDK no IntelliJ

Use o JDK 21 do projeto tanto no Project SDK quanto no Maven Runner.
O Lombok 1.18.42 deste projeto falhou na inicialização do processador com o JDK 27
(`ClassNotFoundException: com.sun.tools.javac.tree.EndPosTable`), impedindo a geração de `builder()`.
Após ajustar o JDK, recarregue o projeto Maven e recompile. O processamento de anotações
já está configurado no `pom.xml`; não é necessário adicionar `@Builder` às classes de teste.

## Integração com PostgreSQL real

```bash
./mvnw -Ppostgres-it verify
```

Executa os 25 testes anteriores e mais 8 testes de integração. O Embedded Postgres
2.2.2 inicia um PostgreSQL 14.22 temporário em uma porta livre e encerra o processo ao
fechar o contexto. Não exige Docker nem acessa o banco configurado para a aplicação.
A primeira execução baixa os binários pelo Maven; execute como usuário comum, não root.

Os testes verificam persistência de usuários, senha e imagem, leitura de votos,
duplicidade sequencial por categoria/ano, troca de vencedor, isolamento entre anos e
categorias, ranking e restrições UNIQUE, NOT NULL e FK. As chamadas dos serviços
confirmam suas próprias transações, permitindo verificar a gravação em consultas seguintes.
A proteção contra votos concorrentes e a migração de um banco antigo não são cobertas.

Validação realizada com Java 25.0.4, compilação para Java 21: 33 testes passaram,
sem falhas, erros ou testes ignorados.
