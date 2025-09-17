package com.georgiiHadzhiev.roomservice.component;

import com.fasterxml.jackson.databind.ser.Serializers;
import com.georgiiHadzhiev.events.BaseEvent;
import com.georgiiHadzhiev.exceptions.EventPublishingException;
import com.georgiiHadzhiev.roomservice.dto.RoomApplicationEvent;
import com.georgiiHadzhiev.utils.JsonLogger;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class BrokerEventPublisher {

    private final KafkaTemplate<String, BaseEvent> kafkaTemplate;
    private final static JsonLogger log = new JsonLogger(BrokerEventPublisher.class);

    public BrokerEventPublisher(KafkaTemplate<String, BaseEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }


    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void publishRoomEvent(RoomApplicationEvent innerEvent) {
        BaseEvent event = innerEvent.getOuterEvent();
        if(event.getAggregateId() == null) throw new EventPublishingException(event);
        kafkaTemplate.send("room.events", String.valueOf(event.getAggregateId()), event);
        log.info("Successfully sent room event",event);
    }
}
