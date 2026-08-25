# mecaniQA-api

API fundacional da MecâniQA desenvolvida com Spring Boot e armazenamento em memória.

## Estrutura

- `br.com.mecaniQA.api.model`: modelos de domínio (`Peca`, `Servico`, `CategoriaPeca`)
- `br.com.mecaniQA.api.repository`: repositórios Singleton em memória
- `br.com.mecaniQA.api.controller`: rotas REST da API
- `br.com.mecaniQA.api.dto`: contratos de entrada para criação e atualização

## Rotas

### Peças

- `POST /api/pecas`
- `GET /api/pecas`
- `GET /api/pecas/{codigo}`
- `PUT /api/pecas/{codigo}`
- `DELETE /api/pecas/{codigo}`

### Serviços

- `POST /api/servicos`
- `GET /api/servicos`
- `GET /api/servicos/{codigo}`
- `PUT /api/servicos/{codigo}`
- `DELETE /api/servicos/{codigo}`

## Execução

O projeto foi validado com `./gradlew.bat test` usando Java 21.
