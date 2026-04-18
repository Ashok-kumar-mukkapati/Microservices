package Product.product_service.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

    private static final Logger log = LoggerFactory.getLogger(KafkaConsumerService.class);

    private final KafkaEventStoreService kafkaEventStoreService;

    public KafkaConsumerService(KafkaEventStoreService kafkaEventStoreService) {
        this.kafkaEventStoreService = kafkaEventStoreService;
    }

    @KafkaListener(topics = "cart-events")
    public void consumeCartEvent(String message) {
        try {
            log.info("==================================================");
            log.info("Kafka event received in product-service: {}", message);
            log.info("==================================================");

            kafkaEventStoreService.addEvent(message);

        } catch (Exception e) {
            log.error("Error while processing Kafka event: {}", message, e);
        }
    }
}