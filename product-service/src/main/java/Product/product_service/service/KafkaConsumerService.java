package Product.product_service.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import Product.product_service.dto.CartEvent;

@Service
public class KafkaConsumerService {

    @KafkaListener(topics = "cart-events", groupId = "product-service-group")
    public void consumeCartEvent(CartEvent cartEvent) {
        System.out.println("Kafka event received in product-service: " + cartEvent);
    }
}