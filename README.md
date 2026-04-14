## 1L Validation + Exception + Logging

### Validation
- quantity must not be null
- quantity must be greater than 0

### Exception Handling
- global exception handler added using `@RestControllerAdvice`

### Logging
- API calls are logged in controllers
- service-level business flow is logged
- validation and runtime errors are logged
- Kafka producer and consumer events are logged

### Validation Done
- invalid quantity requests return proper 400 errors
- error messages are clear
- logs print clearly in console