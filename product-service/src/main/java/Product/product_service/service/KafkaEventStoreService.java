package Product.product_service.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class KafkaEventStoreService {

    private final List<String> events = Collections.synchronizedList(new ArrayList<>());

    public void addEvent(String event) {
        events.add(event);
    }

    public List<String> getEvents() {
        return new ArrayList<>(events);
    }

    public int getEventCount() {
        return events.size();
    }

    public void clearEvents() {
        events.clear();
    }
}