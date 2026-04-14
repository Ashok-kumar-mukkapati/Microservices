package Product.product_service.service;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

@Service
public class KafkaConsumerService {

    private static final Logger log = LoggerFactory.getLogger(KafkaConsumerService.class);

    private final KafkaEventStoreService kafkaEventStoreService;

    private KafkaConsumer<String, String> consumer;
    private Thread consumerThread;
    private volatile boolean running = true;

    public KafkaConsumerService(KafkaEventStoreService kafkaEventStoreService) {
        this.kafkaEventStoreService = kafkaEventStoreService;
    }

    @PostConstruct
    public void startConsumer() {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "product-service-manual-group");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());

        consumer = new KafkaConsumer<>(props);

        consumerThread = new Thread(() -> {
            try {
                TopicPartition partition = new TopicPartition("cart-events", 0);

                consumer.assign(Collections.singletonList(partition));
                consumer.seekToBeginning(Collections.singletonList(partition));

                log.info("Manual Kafka consumer started for topic: cart-events partition: 0");

                while (running) {
                    ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(1));

                    for (ConsumerRecord<String, String> record : records) {
                        String message = record.value();

                        kafkaEventStoreService.addEvent(message);

                        log.info("==================================================");
                        log.info("Kafka event received in product-service: {}", message);
                        log.info("Topic: {}", record.topic());
                        log.info("Partition: {}", record.partition());
                        log.info("Offset: {}", record.offset());
                        log.info("Key: {}", record.key());
                        log.info("==================================================");
                    }
                }
            } catch (Exception e) {
                log.error("Kafka manual consumer error", e);
            } finally {
                try {
                    consumer.close();
                } catch (Exception ignored) {
                }
            }
        });

        consumerThread.setDaemon(true);
        consumerThread.start();
    }

    @PreDestroy
    public void stopConsumer() {
        running = false;
        if (consumer != null) {
            consumer.wakeup();
        }
    }
}