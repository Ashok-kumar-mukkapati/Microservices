## 1K CompletableFuture (Async Processing)

cart-service uses `CompletableFuture` for async processing while adding cart items.

### Async Flow
- fetch cart asynchronously
- fetch product asynchronously
- validate stock asynchronously
- combine results and save cart item

### Endpoints
- `POST /carts/items`
- `POST /carts/items/async`

### Validation Done
- parallel async execution works
- stock validation works
- valid item saves successfully
- invalid quantity fails properly