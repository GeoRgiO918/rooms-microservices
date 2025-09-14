package com.georgiiHadzhiev.fileservice.component;

import com.georgiiHadzhiev.events.BaseEvent;
import com.georgiiHadzhiev.fileservice.service.EventProccesingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class BrokerEventConsumer {

    private final EventProccesingService processingService;
    private static final Logger log = LoggerFactory.getLogger(BrokerEventConsumer.class);

    public BrokerEventConsumer(EventProccesingService processingService) {
        this.processingService = processingService;
    }

    @KafkaListener(topics = "room.events")
    void listener(@Payload BaseEvent event,
                  Acknowledgment acknowledgment
                ){
        try {
            processingService.process(event);
            acknowledgment.acknowledge();
        }catch (Exception e){
            log.error("Error processing event: {}", event, e);
        }


    }
}
