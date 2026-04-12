package Cart.cart_service.service;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import Cart.cart_service.dto.CartEvent;

@Service
public class KafkaProducerService {

    private static final String TOPIC_NAME = "cart-events";

    private final KafkaTemplate<String, CartEvent> kafkaTemplate;

    public KafkaProducerService(KafkaTemplate<String, CartEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendCartEvent(CartEvent cartEvent) {
        kafkaTemplate.send(TOPIC_NAME, cartEvent);
        kafkaTemplate.flush();
        System.out.println("Kafka event sent successfully: " + cartEvent);
    }
}