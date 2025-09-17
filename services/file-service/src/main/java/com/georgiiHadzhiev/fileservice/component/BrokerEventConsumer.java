package com.georgiiHadzhiev.fileservice.component;

import com.georgiiHadzhiev.events.BaseEvent;
import com.georgiiHadzhiev.fileservice.service.EventProccesingService;
import com.georgiiHadzhiev.utils.JsonLogger;
import jdk.security.jarsigner.JarSigner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class BrokerEventConsumer {


    private final EventProccesingService processingService;
    private static final JsonLogger log = new JsonLogger(BrokerEventConsumer.class);

    public BrokerEventConsumer(EventProccesingService processingService) {
        this.processingService = processingService;
    }

    @KafkaListener(topics = "room.events")
    void listener(@Payload BaseEvent event,
                  Acknowledgment acknowledgment
                ){
        log.info("Received event:",event);
        try {
            processingService.process(event);
            acknowledgment.acknowledge();
            log.info("Successfully processed event:", event.getEventId());
        }catch (Exception e){
            log.error("Error processing event:", event, e);
        }


    }
}
