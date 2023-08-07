# Stock API

## Objetivo
<p>Realizar o gerenciamento de estoque</p>

## Funcionalidades
<ul>
  <li>Cadastro e edição de usuários</li>
  <li>Cadastro e edição de categorias de itens</li>
  <li>Cadastro e edição de itens</li>
  <li>Consulta de categorias de itens</li>
  <li>Consulta de itens</li>
  <ul>
    <li>Consulta de todos os itens</li>
    <li>Consulta por nome</li>
    <li>Consulta por status</li>
  </ul>
  <li>Histórico de alterações das informações dos itens</li>
  <ul>
    <li>Histórico de todos os itens</li>
    <li>Histórico por item</li>
  </ul>
</ul>

## Como executar o projeto utilizando Docker

1. Faça o pull das imagens necessárias em seu computador
```bash
docker pull pedromeerholz/stock-api:2.2.1
docker pull pedromeerholz/stock-api-db:1.0
docker pull redis:7
```
2. Crie uma network do Docker
```bash
docker network create -o com.docker.network.bridge.enable_icc=true stock-api-network
```
3. Rode um container para cada imagem nessa ordem:
```bash
docker run -it --name stock-api-db -p 3306:3306 --network stock-api-network pedromeerholz/stock-api-db:1.0
docker run -d --name redis-db -p 6379:6379 --network stock-api-network redis:7
docker run -it --name stock-api -p 8080:8080 --network stock-api-network pedromeerholz/stock-api:2.2.1
```
ou caso queira que os containers sejam excluídos após serem finalizados, execute:
```bash
docker run -it --rm --name stock-api-db -p 3306:3306 --network stock-api-network pedromeerholz/stock-api-db:1.0
docker run -d --rm --name redis-db -p 6379:6379 --network stock-api-network redis:7
docker run -it --rm --name stock-api -p 8080:8080 --network stock-api-network pedromeerholz/stock-api:2.2.1
```

## Acessar a documentação da API
Para acessar a documentação da API, acesse em seu navegador:
```bash
http://localhost:8080/swagger-ui/index.html#/
```

## Requests Postman
Disponibilizei no diretório postman_requests um arquivo .json com toda a coleção de requests utilizadas no Postman durante o desenvolvimento da aplicação. Para utilizá-lo basta abrir o Postman em seu computador e importar o arquivo json. O Postman irá criar a coleção para você juntamente com todas as requests.

### Roteiro de execução do Postman
1. Crie um usuário
2. Faça o login e copie o token de acesso
3. Crie uma categoria de item. Sem ela você não poderá cadastrar nenhum item.
4. Agora você já pode cadastrar, editar e listar itens.

#### Observações
* Antes de fazer a requisição, verifique se ela necessita do token e do email do usuário. Caso preciso, altere ambos para poder ter acesso aos endpoints.
* Se tiver dúvidas sobre a estrutura das requests, acesse a documentação (http://localhost:8080/swagger-ui/index.html#/)