package com.georgiiHadzhiev.roomservice.service;

import com.georgiiHadzhiev.events.RoomEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class RoomEventProducer {

    private final KafkaTemplate<String, RoomEvent> kafkaTemplate;

    public RoomEventProducer(KafkaTemplate<String, RoomEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendRoomEvent(RoomEvent event) {
        kafkaTemplate.send("room.events", String.valueOf(event.getRoomId()), event);
    }
}
