package com.georgiiHadzhiev.fileservice.component;

import com.fasterxml.jackson.databind.ser.Serializers;
import com.georgiiHadzhiev.events.BaseEvent;
import com.georgiiHadzhiev.exceptions.EventPublishingException;
import com.georgiiHadzhiev.fileservice.dto.FileApplicationEvent;
import com.georgiiHadzhiev.payloads.Payload;
import com.georgiiHadzhiev.utils.JsonLogger;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class BrokerEventPublisher {

    private final KafkaTemplate<String, BaseEvent<? extends Payload>> kafkaTemplate;
    private final static JsonLogger log = new JsonLogger(BrokerEventPublisher.class);

    public BrokerEventPublisher(KafkaTemplate<String, BaseEvent<? extends Payload>> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }


    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void publishRoomEvent(FileApplicationEvent innerEvent) {
        BaseEvent<? extends Payload> event = innerEvent.getOuterEvent();
        String aggregateId = "0";
        if(event.getAggregateId() != null) aggregateId = event.getAggregateId();
        kafkaTemplate.send(event.getTopic(), String.valueOf(event.getAggregateId()), event);
        log.info("Successfully sent file event",event);
    }
}
