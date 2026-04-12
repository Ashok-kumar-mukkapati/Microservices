\# Microservices Exercise



\## Completed Steps

\- 1A Setup

\- 1B Entity Layer

\- 1C Repository Layer

\- 1D Service Layer

\- 1E Controller Layer (CRUD APIs)



\## Services

\- product-service (Port 8081)

\- cart-service (Port 8082)



\## Databases

\- product\_db

\- cart\_db



\## Tables

\### product\_db

\- products



\### cart\_db

\- carts

\- cart\_items



\## Layered Architecture

Both services follow layered architecture:



\- Controller

\- Service

\- Repository

\- Entity



\## Service Layer

\### product-service

`ProductService` includes:

\- create product

\- get all products

\- get product by id

\- update product

\- delete product

\- validate stock



\### cart-service

`CartService` includes:

\- create cart

\- get all carts

\- get cart by id

\- add cart item

\- get all cart items

\- delete cart

\- delete cart item



\## Controller Layer APIs



\### Product Service APIs

\- `POST /products`

\- `GET /products`

\- `GET /products/{id}`

\- `PUT /products/{id}`

\- `DELETE /products/{id}`



\### Cart Service APIs

\- `POST /carts`

\- `GET /carts`

\- `GET /carts/{id}`

\- `POST /carts/items`

\- `GET /carts/items`

\- `DELETE /carts/{id}`

\- `DELETE /carts/items/{id}`

