package Cart.cart_service.service;

import java.util.concurrent.TimeUnit;

import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import Cart.cart_service.dto.CartEvent;

@Service
public class KafkaProducerService {

    private static final String TOPIC_NAME = "cart-events";

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public KafkaProducerService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = new ObjectMapper();
    }

    public void sendCartEvent(CartEvent cartEvent) {
        try {
            String jsonMessage = objectMapper.writeValueAsString(cartEvent);

            SendResult<String, String> result =
                    kafkaTemplate.send(TOPIC_NAME, jsonMessage).get(10, TimeUnit.SECONDS);

            RecordMetadata metadata = result.getRecordMetadata();

            System.out.println("Kafka event ACTUALLY sent successfully: " + jsonMessage);
            System.out.println("Topic: " + metadata.topic()
                    + ", Partition: " + metadata.partition()
                    + ", Offset: " + metadata.offset());

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Kafka send failed: " + e.getMessage());
        }
    }
}