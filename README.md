# Projeto E-commerce Backend com Spring Boot
## 📌 Descrição
API backend desenvolvida com Spring Boot para o gerenciamento de um sistema de e-commerce no contexto de um supermercado.  
O projeto foi criado com foco em aprendizado prático, aplicando conceitos de arquitetura em camadas, persistência de dados com JPA/Hibernate e boas práticas no desenvolvimento de APIs REST.

## 🛠️ Tecnologias
- Java
- Spring Boot
- Spring Data JPA
- Hibernate
- MySQL
- Maven

## ⚙️ Funcionalidades
- Cadastro de usuarios de e-commerce
- Cadastro de pedidos de e-commerce
- Listagem de pedidos com paginação
- Detalhamento completo dos pedidos
- Relacionamento entre pedidos e produtos
- Persistência de dados utilizando Spring Data JPA
- Mapeamento de relacionamentos entre entidades:
  - OneToOne
  - OneToMany
  - ManyToOne
  - ManyToMany
- Utilização de chaves compostas com @Embeddable e @EmbeddedId
- Configuração de tabelas de associação com @JoinColumn e @JoinTable
- Definição de restrições de unicidade com @UniqueConstraint
- Uso de estratégias de Cascade
- Otimização de carregamento de dados com Eager e Lazy Loading

## 🚀 Execução do projeto
### ✅ Pré-requisitos
- Java 17
- Maven
- Docker e Docker Compose
- MySQL 8.0
- IDE (IntelliJ IDEA, VS Code ou similar)

### 📄 Dados iniciais
O projeto utiliza o arquivo `src/main/resources/data.sql` para inserir dados iniciais no banco de dados automaticamente ao iniciar a aplicação.  
Esses dados são utilizados para facilitar testes e validar os relacionamentos entre as entidades.

### 🐬 Banco de Dados (MySQL com Docker)
O projeto utiliza MySQL como banco de dados, executado via Docker.  
A criação do banco e do usuário é feita manualmente dentro do container.

1. Suba o container do MySQL:
```bash
docker-compose up -d
```
2. Verifique se o container está em execução:
```bash
docker ps
```
3. Acesse o MySQL como usuário root:
```bash
docker exec -it my-mysql-db mysql -u root -p
```
Senha:
```bash
Password@25
```
4. Crie o usuário e o banco de dados:
```bash
CREATE USER 'erly2'@'%' IDENTIFIED BY '123456';
CREATE DATABASE ecomercedb;
GRANT ALL PRIVILEGES ON ecomercedb.* TO 'erly2'@'%' WITH GRANT OPTION;
FLUSH PRIVILEGES;
```
5. Acesse o banco com o usuário criado:
```bash
docker exec -it my-mysql-db bash
mysql -u erly2 -p ecomercedb
```
6. Exemplo de consulta:
```bash
SHOW TABLES;
SELECT * FROM tb_products;
```
7. Após a verificação, o container pode ser finalizado:
```bash
docker-compose down
```
### ▶️ Executando o projeto
```bash
mvn spring-boot:run

###  O execute a classe principal:
EcomerceApplication
```

## API Endpoints

Para fazer as requisições HTTP abaixo, foi utilizada a ferramenta [postman](https://www.postman.com/):

A API poderá ser acessada em [localhost:8080](http://localhost:8080).

- POST /users
```
http://localhost:8080/users
{
    "fullName" : "Erly",
    "address" : "Rua",
    "number" : "200",
    "complement" : "b2 apt200"
}

Status 201 Ok
Location: /users/f6358f17-ab99-4d35-acf6-cb948b6fbc28
Content-Length: 0
Date: Tue, 16 Dec 2025 20:18:49 GMT
Keep-Alive: timeout=60
Connection: keep-alive
```
- POST /orders
```
http://localhost:8080/orders
{
    "userId" : "f6358f17-ab99-4d35-acf6-cb948b6fbc28",
    "items" : [
        {
            "productId" : 1,
            "quantity" : 2
        },
        {
            "productId" : 2,
            "quantity" : 1
        }
    ]
}

Status 201 Ok
Location: /orders/4
Content-Length: 0
Date: Tue, 16 Dec 2025 20:23:40 GMT
Keep-Alive: timeout=60
Connection: keep-alive
```
- GET /orders
```
http://localhost:8080/orders

Status 200 Ok
Content-Type: application/json
Transfer-Encoding: chunked
Date: Tue, 16 Dec 2025 20:25:23 GMT
Keep-Alive: timeout=60
Connection: keep-alive

{
    "data": [
        {
            "orderId": 1,
            "orderData": "2025-12-12T16:20:53.761755",
            "userId": "8fe03f01-bbfd-4af9-ad77-a22a055dd33d",
            "total": 6500.50
        },
        {
            "orderId": 2,
            "orderData": "2025-12-12T16:24:35.440971",
            "userId": "8fe03f01-bbfd-4af9-ad77-a22a055dd33d",
            "total": 6500.50
        },
        {
            "orderId": 3,
            "orderData": "2025-12-15T16:23:11.172892",
            "userId": "8fe03f01-bbfd-4af9-ad77-a22a055dd33d",
            "total": 11601.00
        },
        {
            "orderId": 4,
            "orderData": "2025-12-16T17:23:40.491325",
            "userId": "f6358f17-ab99-4d35-acf6-cb948b6fbc28",
            "total": 11001.00
        }
    ],
    "pagination": {
        "page": 0,
        "pageSize": 10,
        "totalElements": 4,
        "totalPages": 1
    }
}
```

- GET /users/{id}
```
http://localhost:8080/users/f6358f17-ab99-4d35-acf6-cb948b6fbc28

Status 200 Ok
Content-Type: application/json
Transfer-Encoding: chunked
Date: Tue, 16 Dec 2025 20:30:08 GMT
Keep-Alive: timeout=60
Connection: keep-alive

{
    "billingAddress": {
        "address": "Rua",
        "billingAddressId": 2,
        "complement": "b2 apt200",
        "number": "200"
    },
    "fullName": "Erly",
    "userId": "f6358f17-ab99-4d35-acf6-cb948b6fbc28"
}
```

