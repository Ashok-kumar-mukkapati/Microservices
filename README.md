# Microservices Exercise

## Completed Steps
- 1A Setup
- 1B Entity Layer
- 1C Repository Layer
- 1D Service Layer
- 1E Controller Layer (CRUD APIs)
- 1F Pagination + Sorting + Java Streams
- 1G Native Query
- 1H WebClient Integration

## Services
- product-service (Port 8081)
- cart-service (Port 8082)

## Databases
- product_db
- cart_db

## Tables
### product_db
- products

### cart_db
- carts
- cart_items

## 1H WebClient Integration
`cart-service` now calls `product-service` using Spring WebClient.

### Flow
Client → cart-service → product-service → validation → save cart item

### Validation Done
- product existence check
- stock availability check

### APIs Used
#### product-service
- `GET /products/{id}`

#### cart-service
- `POST /carts/items`
- `GET /carts/items`

### WebClient Base URL
- `http://localhost:8081`

## 1H WebClient Integration

cart-service calls product-service using Spring WebClient.

### Validations:
- Product existence check
- Stock availability check

### APIs:
- POST /carts/items
- GET /carts/items