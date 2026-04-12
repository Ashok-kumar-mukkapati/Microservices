# Microservices Exercise

## Completed Steps
- 1A Setup
- 1B Entity Layer
- 1C Repository Layer
- 1D Service Layer
- 1E Controller Layer (CRUD APIs)
- 1F Pagination + Sorting + Java Streams
- 1G Native Query

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

## 1G Native Query Feature
Added a native SQL query in `ProductRepository` to fetch all products whose price is greater than a given input value.

### Native Query API
- `GET /products/price-greater-than?price=1000`

### SQL Used
```sql
SELECT * FROM products WHERE price > ?1