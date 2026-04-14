package Product.product_service.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Product.product_service.service.KafkaEventStoreService;

@RestController
@RequestMapping("/kafka")
public class KafkaDebugController {

    private final KafkaEventStoreService kafkaEventStoreService;

    public KafkaDebugController(KafkaEventStoreService kafkaEventStoreService) {
        this.kafkaEventStoreService = kafkaEventStoreService;
    }

    @GetMapping("/events")
    public ResponseEntity<List<String>> getEvents() {
        return ResponseEntity.ok(kafkaEventStoreService.getEvents());
    }

    @GetMapping("/count")
    public ResponseEntity<Integer> getEventCount() {
        return ResponseEntity.ok(kafkaEventStoreService.getEventCount());
    }

    @DeleteMapping("/events")
    public ResponseEntity<String> clearEvents() {
        kafkaEventStoreService.clearEvents();
        return ResponseEntity.ok("Kafka stored events cleared");
    }
}