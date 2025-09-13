package com.georgiiHadzhiev.fileservice.component;

import com.georgiiHadzhiev.events.BaseEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class BrokerEventConsumer {


    @KafkaListener(topics = "room.events")
    void listener(BaseEvent event,
                  @Header(KafkaHeaders.RECEIVED_PARTITION) int partition,
                  @Header(KafkaHeaders.OFFSET) int offset,
                  Acknowledgment ack){
        System.out.println("Recievied event: " + event.toString() );

    }
}
