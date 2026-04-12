# Microservices Exercise

## Completed Steps
- 1A Setup
- 1B Entity Layer
- 1C Repository Layer
- 1D Service Layer
- 1E Controller Layer (CRUD APIs)
- 1F Pagination + Sorting + Java Streams

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

## Layered Architecture
Both services follow layered architecture:

- Controller
- Service
- Repository
- Entity

## 1F Features Added
In `product-service`:
- Pagination using Spring Data JPA
- Sorting using `Pageable` and `Sort`
- Java Streams filtering by keyword
- Java Streams filtering by minimum price
- Java Streams transformation from `Product` to `ProductDTO`

## New API
### Product Service
- `GET /products/paged?page=0&size=5&sortBy=price&sortDir=asc`
- optional query params:
  - `keyword`
  - `minPrice`