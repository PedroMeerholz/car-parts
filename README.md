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
  <li>Histórico de alterações das informações dos itens</li>
</ul>

## Como executar o projeto

Para executar o projeto será necessário ter instalado o Docker em seu computador. Caso você não tenha o Docker instalado, você deverá ter:
* Java 17
* Maven 3.6.3 (ou superior)

### Maneira 1 - Sem Docker
1. Faça o clone do projeto utilizando git
2. No diretório raiz do projeto, execute o comando:
```bash
mvn spring-boot:run
```

### Maneira 2 - Com Docker
1. Faça o pull das imagens necessárias em seu computador
```bash
docker pull pedromeerholz/stock-api:2.2
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
docker run -it --name stock-api -p 8080:8080 --network stock-api-network pedromeerholz/stock-api:2.1.1
```
ou caso queira que os containers sejam excluídos após serem finalizados, execute:
```bash
docker run -it --rm --name stock-api-db -p 3306:3306 --network stock-api-network pedromeerholz/stock-api-db:1.0
docker run -d --rm --name redis-db -p 6379:6379 --network stock-api-network redis:7
docker run -it --rm --name stock-api -p 8080:8080 --network stock-api-network pedromeerholz/stock-api:2.1.1
```
4. Para acessar a documentação da API, acesse em seu navegador:
```bash
http://localhost:8080/swagger-ui/index.html#/
```

## Requests Postman
Disponibilizei no diretório postman_requests um arquivo .json com toda a coleção de requests utilizadas no Postman durante o desenvolvimento da aplicação. Para utilizá-lo basta abrir o Postman em seu computador e importar o arquivo json. O Postman irá criar a coleção para você juntamente com todas as requests.