package com.georgiiHadzhiev.roomservice.component;

import com.georgiiHadzhiev.events.BaseEvent;
import com.georgiiHadzhiev.exceptions.EventPublishingException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class RoomEventProducer {

    private final KafkaTemplate<String, BaseEvent> kafkaTemplate;

    public RoomEventProducer(KafkaTemplate<String, BaseEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendRoomEvent(BaseEvent event) {
        if(event.getAggregateId() == null) throw new EventPublishingException(event);
        kafkaTemplate.send("room.events", String.valueOf(event.getAggregateId()), event);
    }
}
