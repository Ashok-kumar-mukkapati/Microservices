package Cart.cart_service.service;

import java.util.concurrent.TimeUnit;

import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
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
        try {
            SendResult<String, CartEvent> result =
                    kafkaTemplate.send(TOPIC_NAME, cartEvent).get(10, TimeUnit.SECONDS);

            RecordMetadata metadata = result.getRecordMetadata();

            System.out.println("Kafka event ACTUALLY sent successfully: " + cartEvent);
            System.out.println("Topic: " + metadata.topic()
                    + ", Partition: " + metadata.partition()
                    + ", Offset: " + metadata.offset());

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Kafka send failed: " + e.getMessage());
        }
    }
}