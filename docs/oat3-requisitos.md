# OAT 3: persistência e encerramento

## Respostas objetivas da especificação

- Persistência obrigatória em MySQL por JDBC manual; ORM, JPA, Hibernate e Spring Data não são permitidos.
- Repositórios implementam contrato genérico próprio para salvar, localizar, listar, atualizar e excluir.
- Mappers convertem cada `ResultSet` em objetos de domínio.
- Controllers chamam Services; a lógica e o acesso aos repositórios ficam na camada Service.
- Exceções de negócio são próprias e tratadas sem `@ExceptionHandler` ou advice global.
- O banco e o script versionado acompanham a aplicação. A branch avaliada é `main`.
- A apresentação deve ficar em `apresentacoes/mecaniQA_api_oat3_<nome_da_equipe>.pdf` e também ser submetida no Blackboard.
- A apresentação oral dura no máximo sete minutos e é feita por uma pessoa escolhida pelo professor.

## Atividade do Template Method

A classe abstrata `RepositorioJdbcTemplate` controla a abertura e o fechamento da conexão e traduz falhas SQL para a exceção de persistência da aplicação. Cada repositório implementa os passos particulares da query e do mapeamento. `try-with-resources` fecha conexão, statements e result sets mesmo quando uma operação falha.

## Estado no código

- Peça, Serviço, Ordem de Serviço e Pedido de Peças têm repositórios JDBC que implementam `Repositorio<T, ID>`.
- Os mappers de `ResultSet` ficam em classes próprias.
- Peças e Serviços expõem operações de criar, listar, consultar, atualizar e excluir. OS e Pedidos permitem criar, listar, consultar, atualizar estado/itens e excluir.
- O contrato JSON dos DTOs existentes foi preservado.
- Use `script_mecaniqa.sql` para criar o schema MySQL. As requisições da API precisam ser verificadas com o banco configurado.
