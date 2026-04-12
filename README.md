## 1I Kafka Producer

When a valid cart item is added in `cart-service`, a Kafka event is published to the `cart-events` topic.

### Kafka Topic
- `cart-events`

### Event Example
```json
{
  "cartId": 1,
  "productId": 1,
  "quantity": 2,
  "eventType": "ITEM_ADDED"
}