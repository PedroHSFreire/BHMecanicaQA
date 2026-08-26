# Roteiro de Apresentacao da MecaniQA API

Este roteiro ajuda a explicar o projeto em uma apresentacao, avaliacao ou demonstracao tecnica. A ordem foi montada para contar a historia do sistema antes de entrar nos detalhes do codigo.

## 1. Abertura: objetivo do projeto

Sugestao de fala:

> Esta e a API fundacional da MecaniQA. O objetivo e disponibilizar o cadastro e o gerenciamento de pecas e servicos de uma oficina mecanica por meio de endpoints REST. Nesta primeira versao, os dados ficam somente em memoria, por isso o foco esta na estrutura da API e nas regras de negocio basicas.

Pontos para destacar:

- A aplicacao foi feita com Java e Spring Boot.
- A comunicacao acontece por HTTP e os dados sao trocados em JSON.
- Existem dois recursos principais: pecas e servicos.
- O projeto implementa as operacoes CRUD: criar, consultar, atualizar e remover.

## 2. Visao geral da arquitetura

Explique o caminho de uma requisicao:

```text
Cliente HTTP
    |
    v
Controller REST
    |
    v
Repository Singleton
    |
    v
ArrayList em memoria
```

Sugestao de fala:

> Quando um cliente faz uma requisicao, o Spring identifica o controller pelo caminho e pelo verbo HTTP. O controller valida os dados e transforma o DTO em um model. Em seguida, chama o repository, que executa a operacao na lista em memoria e devolve o resultado. O controller entao monta a resposta HTTP adequada.

Observacao importante: este projeto nao possui uma camada `Service`. A regra de validacao esta nos controllers e a persistencia esta nos repositories, conforme a proposta simplificada do projeto.

## 3. Como a aplicacao inicia

Abra `ApiApplication.java` e mostre a classe principal.

Sugestao de fala:

> A classe principal tem `@SpringBootApplication`. Essa anotacao combina configuracao, descoberta dos componentes Spring e inicializacao da aplicacao. O metodo `main` chama `SpringApplication.run`, que sobe o servidor embutido e disponibiliza os endpoints.

Pergunta possivel: por que nao existe configuracao manual do servidor?

Resposta:

> O Spring Boot usa convencoes e um servidor embutido. Por isso, basta executar a classe principal ou usar o Gradle para iniciar a aplicacao.

## 4. Modelos de dominio

Arquivos principais:

- `model/Peca.java`
- `model/Servico.java`
- `model/CategoriaPeca.java`

### Peca

Explique que `Peca` representa um item de estoque. Ela possui codigo, codigo de barras, fornecedor ou marca, quantidade, precos, tamanho, cor, categoria e datas.

### Servico

Explique que `Servico` representa um servico oferecido pela oficina. Ele possui codigo, nome, duracao estimada em minutos, custo tabelado e datas.

### CategoriaPeca

Sugestao de fala:

> `CategoriaPeca` e um `enum`, portanto limita a categoria a valores conhecidos, como `MOTOR`, `FREIOS` e `ELETRICA`. Isso reduz erros de digitacao e deixa o contrato da API mais claro.

Pergunta possivel: por que os precos usam `BigDecimal`?

Resposta:

> Para representar valores monetarios com maior precisao. Tipos de ponto flutuante, como `double`, podem gerar imprecisoes em calculos de dinheiro.

## 5. DTOs: entrada da API

Arquivos:

- `dto/PecaRequest.java`
- `dto/ServicoRequest.java`

Sugestao de fala:

> Os DTOs sao os objetos usados para receber o corpo de uma requisicao. Eles sao diferentes dos models porque o cliente nao deve definir campos controlados pelo sistema, como o codigo e as datas. Os DTOs sao `record`, uma forma concisa do Java para objetos imutaveis de transporte de dados.

Pergunta possivel: por que nao receber `Peca` diretamente?

Resposta:

> Separar entrada e dominio evita que o cliente tente enviar ou alterar identificadores e datas. Tambem deixa mais facil mudar o formato da requisicao sem alterar o model interno.

## 6. Controllers e rotas

### `PecaController`

O controller e identificado por `@RestController` e todas as rotas comecam em `/api/pecas`.

| Verbo e rota | Funcao | Resposta esperada |
| --- | --- | --- |
| `POST /api/pecas` | Cria uma peca | `201 Created` |
| `GET /api/pecas` | Lista todas | `200 OK` |
| `GET /api/pecas/{codigo}` | Busca uma peca | `200 OK` ou `404 Not Found` |
| `PUT /api/pecas/{codigo}` | Atualiza uma peca | `200 OK` ou `404 Not Found` |
| `DELETE /api/pecas/{codigo}` | Remove uma peca | `204 No Content` ou `404 Not Found` |

### `ServicoController`

O fluxo e o mesmo, mas usando o prefixo `/api/servicos` e os campos especificos de servico.

Como explicar as anotacoes:

- `@RequestMapping` define o prefixo da rota.
- `@PostMapping`, `@GetMapping`, `@PutMapping` e `@DeleteMapping` ligam metodos HTTP a metodos Java.
- `@PathVariable` captura o codigo presente na URL.
- `@RequestBody` converte o JSON recebido em um DTO.
- `ResponseEntity` permite controlar corpo e status HTTP.

## 7. Validacao e status HTTP

Antes de criar ou atualizar, cada controller chama o metodo `validar`.

Regras para pecas:

- corpo obrigatorio;
- codigo de barras e fornecedor ou marca nao podem ficar vazios;
- estoque nao pode ser negativo;
- precos devem ser informados e nao podem ser negativos;
- categoria e obrigatoria.

Regras para servicos:

- corpo obrigatorio;
- nome nao pode ficar vazio;
- duracao deve ser maior que zero;
- custo nao pode ser negativo.

Sugestao de fala:

> A validacao acontece antes de chamar o repository. Se o dado for invalido, a API responde `400 Bad Request` e nao altera a lista. Se o codigo nao existir, a API responde `404 Not Found`. Isso informa ao cliente o resultado real da operacao, em vez de sempre retornar sucesso.

## 8. Repositories Singleton e memoria

Arquivos:

- `repository/PecaRepository.java`
- `repository/ServicoRepository.java`

Sugestao de fala:

> Cada repository possui uma unica instancia, obtida por `getInstance()`. O construtor e privado, a instancia fica em `INSTANCE` e os dados sao armazenados em um `ArrayList`. Assim, controllers diferentes acessam a mesma colecao durante a execucao da aplicacao.

Como o CRUD funciona:

- `create` gera o codigo com `AtomicLong`, define as datas e adiciona uma copia na lista.
- `findAll` devolve uma lista de copias.
- `findByCodigo` procura pelo codigo e retorna um `Optional`.
- `update` localiza o item, altera os campos permitidos e atualiza a data.
- `delete` remove o item pelo codigo e informa se encontrou algo.

Pergunta possivel: por que os metodos sao `synchronized`?

Resposta:

> Como a lista e compartilhada, `synchronized` evita que duas requisicoes alterem a estrutura ao mesmo tempo de forma inconsistente. O `AtomicLong` tambem torna a geracao dos codigos segura para acessos concorrentes.

Pergunta possivel: por que o repository devolve copias?

Resposta:

> Para evitar que o controller ou outro trecho do sistema altere diretamente o objeto que esta dentro da lista. O repository continua sendo o responsavel pelo estado armazenado.

Pergunta possivel: os dados continuam depois que a aplicacao reinicia?

Resposta:

> Nao. Esta e uma persistencia em memoria. Ao desligar ou reiniciar a aplicacao, a lista e criada novamente e os dados sao perdidos. Em uma evolucao, o repository poderia ser substituido por uma implementacao com banco de dados.

## 9. Demonstracao pratica

Inicie a aplicacao e use Postman, Insomnia ou `curl`.

### Criar uma peca

```http
POST http://localhost:8080/api/pecas
Content-Type: application/json
```

```json
{
  "codigoBarras": "789000000001",
  "fornecedorMarca": "Bosch",
  "quantidadeEstoque": 8,
  "precoCusto": 120.50,
  "precoVenda": 189.90,
  "tamanho": "Unico",
  "cor": "Preto",
  "categoria": "ELETRICA"
}
```

Mostre que a resposta possui `codigo` e datas preenchidos pelo servidor.

### Consultar e atualizar

```http
GET http://localhost:8080/api/pecas
GET http://localhost:8080/api/pecas/1
PUT http://localhost:8080/api/pecas/1
```

No `PUT`, use um corpo valido e explique que o codigo vem da URL. Depois consulte novamente para mostrar a alteracao.

### Demonstrar erro

Envie uma peca com `quantidadeEstoque` negativo ou uma categoria ausente. Mostre o `400 Bad Request` e o campo `erro` retornado.

### Criar um servico

```http
POST http://localhost:8080/api/servicos
Content-Type: application/json
```

```json
{
  "nomeServico": "Troca de oleo",
  "tempoEstimadoDuracaoMinutos": 40,
  "custoTabelado": 150.00
}
```

Finalize removendo um registro com `DELETE` e explique o `204 No Content`.

## 10. Fechamento

Sugestao de fala:

> A API entrega um CRUD funcional para pecas e servicos, com separacao entre entrada, dominio, controle HTTP e armazenamento. A escolha da memoria e do Singleton atende ao escopo inicial e torna o funcionamento facil de demonstrar. O proximo passo natural seria adicionar persistencia real, uma camada de servico, tratamento global de erros e testes de cada endpoint.

## Perguntas rapidas adicionais

### O que significa REST?

> E um estilo de comunicacao baseado em recursos, URLs e verbos HTTP. Neste projeto, `pecas` e `servicos` sao recursos e `POST`, `GET`, `PUT` e `DELETE` representam as operacoes.

### O que e CRUD?

> Create, Read, Update e Delete: criar, consultar, atualizar e excluir.

### O que acontece quando busco um codigo inexistente?

> O repository retorna `Optional.empty()` e o controller transforma isso em `404 Not Found` com uma mensagem de erro.

### Por que nao existe `@Autowired`?

> O projeto foi definido para usar o Singleton explicitamente. Por isso, o controller obtem a instancia com `getInstance()` em vez de receber um bean por injecao.

### Qual e uma limitacao atual?

> Nao ha banco de dados, autenticacao, paginacao ou tratamento global para todos os erros de desserializacao. Esses pontos podem ser adicionados em versoes futuras.

### Como executar os testes?

```bash
./gradlew test
```

No Windows:

```powershell
.\gradlew.bat test
```
